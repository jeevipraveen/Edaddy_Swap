package com.mauto.myapplication.prebooking

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mauto.myapplication.R
import com.mauto.myapplication.home.livedata.HomeLiveData
import com.mauto.myapplication.home.model.TransactionData
import com.mauto.myapplication.home.model.TransactionFinalResponse
import com.mauto.myapplication.utils.ApiConstant
import com.mindorks.placeholderview.ExpandablePlaceHolderView
import kotlinx.android.synthetic.main.booking_history.close_view
import kotlinx.android.synthetic.main.booking_history.no_customer_records


class PreBookingHistoryActivity : AppCompatActivity() {

    lateinit var expandablePlaceHolderView: ExpandablePlaceHolderView;

    lateinit var model: HomeLiveData
    lateinit var pd: ProgressDialog
    var distinctByArray: MutableList<TransactionData> = ArrayList<TransactionData>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_history)

        pd = ProgressDialog(this@PreBookingHistoryActivity)
        pd.setMessage("Loading...")
        model = ViewModelProvider(this)[HomeLiveData::class.java]

        expandablePlaceHolderView =
            findViewById<ExpandablePlaceHolderView>(R.id.expandablePlaceHolder)

        loadData()

        expandablePlaceHolderView.setOnClickListener {

            Toast.makeText(applicationContext, "Clicked", Toast.LENGTH_LONG).show()
        }

        close_view.setOnClickListener {

            finish()
        }
    }

    private fun loadData() {

        if (model == null)
            model = ViewModelProvider(this)[HomeLiveData::class.java]
        pd.show()
        //Calling API from Observer
        model.getHistory(ApiConstant.OBJ_HEADER_PRE_BOOKING)
            ?.observe(this, Observer { this.setUserStatus(it) })


    }

    private fun setUserStatus(list: TransactionFinalResponse?) {


        if (list!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()

            distinctByArray = list!!.response!!.data
           // total.text = list!!.response!!.summary!!.total_amount

            no_customer_records.visibility = View.GONE


            var categoryMap = HashMap<String, List<TransactionData>>()



            for (refund: TransactionData in distinctByArray) {
                var bookingList1: MutableList<TransactionData>? =
                    categoryMap[refund.customerName] as MutableList<TransactionData>?
                if (bookingList1 == null) {
                    bookingList1 = ArrayList<TransactionData>()
                }
                bookingList1.add(refund)
                categoryMap[refund.customerName.toString()] = bookingList1
            }



            categoryMap.forEach { (key, value) ->
                println("$key = $value")
                expandablePlaceHolderView.addView(
                    HeaderView(
                        this,
                        key,
                        value[0].txnAmount!!,
                        value[0].gateway!!,
                        value[0].txnDate!!
                    )
                )
                for (movie in value) {
                    expandablePlaceHolderView.addView(
                        ChildView(
                            this,
                            movie.customerName!!,
                            movie.txnAmount!!,
                            movie.gateway!!,
                            movie.txnDate!!
                        )
                    )

                }

            }


        } else {
            if (pd != null)
                pd.dismiss()
            no_customer_records.visibility = View.VISIBLE
        }


    }


}

