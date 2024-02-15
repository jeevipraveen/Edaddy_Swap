package com.mauto.myapplication.cancelhistory

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mauto.myapplication.R
import com.mauto.myapplication.home.adapter.RefundListAdapter
import com.mauto.myapplication.home.livedata.HomeLiveData
import com.mauto.myapplication.home.model.TransactionData
import com.mauto.myapplication.home.model.TransactionFinalResponse
import com.mauto.myapplication.utils.ApiConstant.OBJ_HEADER_CANCELLED
import kotlinx.android.synthetic.main.booking_history.close_view
import kotlinx.android.synthetic.main.cancel_history.*


class CancelHistoryActivity : AppCompatActivity() {

    lateinit var model: HomeLiveData
    lateinit var pd: ProgressDialog
    var distinctByArray: MutableList<TransactionData> = ArrayList<TransactionData>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cancel_history)

        pd = ProgressDialog(this@CancelHistoryActivity)
        pd.setMessage("Loading...")
        model = ViewModelProvider(this)[HomeLiveData::class.java]

        recyclerView.layoutManager = LinearLayoutManager(
            this@CancelHistoryActivity
        )
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        loadValueFromApi()

        close_view.setOnClickListener {

            finish()
        }


    }


    private fun loadValueFromApi() {

        if (model == null)
            model = ViewModelProvider(this)[HomeLiveData::class.java]
        pd.show()
        //Calling API from Observer

        model.getHistory(OBJ_HEADER_CANCELLED)
            ?.observe(this, Observer { this.setUserStatus(it) })


    }

    private fun setUserStatus(list: TransactionFinalResponse) {


        if (list.status.equals("1")) {
            if (pd != null)
                pd.dismiss()

            distinctByArray = list.response!!.data

            no_customer_records.visibility = View.GONE

        } else {
            if (pd != null)
                pd.dismiss()
            no_customer_records.visibility = View.VISIBLE
        }


        var adapter = RefundListAdapter(this@CancelHistoryActivity, distinctByArray)
        recyclerView.adapter = adapter
    }


}

