package com.mauto.myapplication.home

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mauto.myapplication.R
import com.mauto.myapplication.home.adapter.RefundListAdapter
import com.mauto.myapplication.home.livedata.HomeLiveData
import com.mauto.myapplication.home.model.*
import com.mauto.myapplication.utils.*
import kotlinx.android.synthetic.main.fragment_cancellation.*
import java.util.*


class RefundFragment : Fragment() {


    lateinit var pd: ProgressDialog
    lateinit var defaultflag: String
    lateinit var defaultflag1: String
    private val COUNTRY_CODE_REQUEST = 113
    lateinit var phoneCode: ImageView;
    lateinit var iconSearch: ImageView;
    lateinit var recyclerView: RecyclerView;
    lateinit var viewHide1: LinearLayout;
    lateinit var viewHide2: LinearLayout;
    lateinit var userNo: EditText;
    lateinit var btn_cancel: TextView;
    lateinit var mContext: Context;
    lateinit var model: HomeLiveData
    var distinctByArray: MutableList<TransactionData> = ArrayList<TransactionData>();
    var isCustomerSearch = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_cancellation, container, false)

        mContext = rootView.context;

        pd = ProgressDialog(mContext)
        pd.setMessage("Loading...")
        model = ViewModelProvider(this)[HomeLiveData::class.java]


        phoneCode = rootView.findViewById<ImageView>(R.id.phone_code)
        iconSearch = rootView.findViewById<ImageView>(R.id.icon_search)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        viewHide1 = rootView.findViewById<LinearLayout>(R.id.customer_records)
        viewHide2 = rootView.findViewById<LinearLayout>(R.id.no_customer_records)
        userNo = rootView.findViewById<EditText>(R.id.userNo)
        btn_cancel = rootView.findViewById<TextView>(R.id.btn_cancel)


        val selCode = MAutoSharedPref.getAppPrefs(mContext).getStringValue("MAT_CODE")

        val selCountry = MAutoSharedPref.getAppPrefs(mContext).getStringValue("MAT_COUNTRY")

        defaultflag = "$selCode,$selCountry"
        defaultflag1 = selCountry

        val imageBuilder = StringBuilder()
        imageBuilder.append("flags/flag_").append(defaultflag1.lowercase(Locale.getDefault())).append(".png")
        val inputStream = mContext.assets.open(imageBuilder.toString())
        val bitmap = BitmapFactory.decodeStream(inputStream)
        phoneCode?.setImageBitmap(bitmap)


        recyclerView.layoutManager = LinearLayoutManager(
            mContext
        )
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )


        iconSearch.setOnClickListener {
            AppUtils.hideKeyboard(mContext as Activity, userNo!!)
            if (userNo.text.length > 4) {
                isCustomerSearch = true
                getCustomer(userNo.text.toString())
            } else {
                ToastUtils.showToast(mContext, "Please enter valid mobile No")
            }


        }

        userNo.doAfterTextChanged {
            if (isCustomerSearch) {
                isCustomerSearch = false
                viewHide1.visibility = View.GONE
                viewHide2.visibility = View.GONE
                userNo.text.clear()
                if (model.clearCustomerData().hasObservers()) {
                    model.clearCustomerData().removeObservers(this)
                }
            }
        }

        loadValueFromApi()

        phoneCode.setOnClickListener {

          //  countryIntent()
        }

        btn_cancel.setOnClickListener {

            if (reason_txt.text.toString().isEmpty()) {
                ToastUtils.showToast(mContext, "Please add reason for cancellation")
            } else {

                MAutoSharedPref.getAppPrefs(mContext)
                    .saveStringValue(PrefConstant.MAT_CANCEL_REASON, reason_txt.text.toString())

                cancelPayment()

            }
        }



        //loadValueFromApi()

        return rootView

    }


    private fun loadValueFromApi() {

        if (model == null)
            model = ViewModelProvider(this)[HomeLiveData::class.java]
        //Calling API from Observer

        model.getHistory(ApiConstant.OBJ_HEADER_CANCELLED)
            ?.observe(viewLifecycleOwner, Observer { this.setUserStatus(it) })


    }

    private fun setUserStatus(list: TransactionFinalResponse) {


        if (list.status.equals("1")) {

            distinctByArray = list.response!!.data

            nor_records.visibility = View.GONE

        } else {
            if (pd != null)
                pd.dismiss()
            nor_records.visibility = View.VISIBLE
        }


        var adapter = RefundListAdapter(mContext, distinctByArray)
        recyclerView.adapter = adapter
    }


    fun countryIntent() {
        val intent_book_now = Intent(mContext, CountryCodeSelection::class.java)
        startActivityForResult(intent_book_now, COUNTRY_CODE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_FIRST_USER) {

            defaultflag = data?.getStringExtra(ApiConstant.INTENT_OBJECT).toString()
            defaultflag1 = data?.getStringExtra(ApiConstant.INTENT_OBJECT1).toString()
            val imageBuilder = StringBuilder()
            imageBuilder.append("flags/flag_").append(defaultflag1.toLowerCase()).append(".png")
            val inputStream = mContext.assets.open(imageBuilder.toString())
            val bitmap = BitmapFactory.decodeStream(inputStream)
            phoneCode?.setImageBitmap(bitmap)
            Log.d("Cancel==>", "=$defaultflag")


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun getCustomer(number: String) {
        val split = defaultflag.split(",")
        //Declaring View model
        if (model == null)
            model = ViewModelProvider(this)[HomeLiveData::class.java]
        pd.show()
        var isPlusCode = "+"
        MAutoSharedPref.getAppPrefs(mContext)
            .saveStringValue(PrefConstant.MAT_CUSTOMER_PHONE_CODE, "+" + split[0])
        MAutoSharedPref.getAppPrefs(mContext)
            .saveStringValue(PrefConstant.MAT_CUSTOMER_PHONE_NO, number)

        isPlusCode = if (split[0].contains("+")) {
            split[0]
        } else {
            "+" + split[0]
        }
        model.getCustomerDetails(isPlusCode, number)
            ?.observe(viewLifecycleOwner, Observer { this.setStatus(it) })


    }

    private fun setStatus(userStatus: CustomerResponse?) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()

            if (model.clearCustomerData().hasObservers()) {
                model.clearCustomerData().removeObservers(this)
            }

            if(userStatus.response!!.type.equals("prebooking")){
                viewHide1.visibility = View.VISIBLE
                viewHide2.visibility = View.GONE
            }
            else{
                viewHide1.visibility = View.GONE
                viewHide2.visibility = View.VISIBLE
                ToastUtils.showToast(mContext, "Sorry only working for prebooking")
            }



            customerName.text = userStatus.response.name + " "
            //+ userStatus.response.surname
            customerID.text = "ID : " + userStatus.response.leadId

            val selectedModel = userStatus.response.selectedModel
            vehicle_type.text = selectedModel

            MAutoSharedPref.getAppPrefs(mContext)
                .saveStringValue(PrefConstant.MAT_CUSTOMER_ID, userStatus.response.id)

        } else {
            if (pd != null)
                pd.dismiss()
            if (model.clearCustomerData().hasObservers()) {
                model.clearCustomerData().removeObservers(this)
            }
            viewHide1.visibility = View.GONE
            viewHide2.visibility = View.VISIBLE
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()

    }


    private fun cancelPayment() {
        if (model == null)
            model = ViewModelProvider(this)[HomeLiveData::class.java]
        pd.show()
        //Calling API from Observer
        model.cancelPayment()
            ?.observe(viewLifecycleOwner, Observer { this.setStatus(it) })


    }

    private fun setStatus(userStatus: CancelPaymentResponse?) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()
            if (model.clearDataCancelPayment().hasObservers()) {
                model.clearDataCancelPayment().removeObservers(this)
            }

            viewHide1.visibility = View.GONE
            viewHide2.visibility = View.GONE
            userNo.text.clear()
            ToastUtils.showToast(
                mContext,
                "Pre Booking request has been accepted will update you on refund status"
            )
        } else {
            if (pd != null)
                pd.dismiss()
          //  reloadFragment()
            if (model.clearDataCancelPayment().hasObservers()) {
                model.clearDataCancelPayment().removeObservers(this)
            }
            ToastUtils.showToast(mContext, "Unable to cancel the Payment")
        }
    }

    fun reloadFragment()
    {
        val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false)
        }
        ft.detach(this).attach(this).commit()
    }
}