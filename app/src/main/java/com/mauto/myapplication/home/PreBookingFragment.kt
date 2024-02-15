package com.mauto.myapplication.home

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mauto.myapplication.R
import com.mauto.myapplication.home.livedata.HomeLiveData
import com.mauto.myapplication.home.model.CustomerResponse
import com.mauto.myapplication.home.model.PaymentResponse
import com.mauto.myapplication.home.model.PaymentTypeResponse
import com.mauto.myapplication.utils.*
import kotlinx.android.synthetic.main.fragment_prebooking.*
import java.util.*


class PreBookingFragment : Fragment() {

    val handler = Handler()
    var runnable: Runnable? = null

    var timeForPayment: Long = 18000
    var timeForPaymentTotal: Int = 1
    var timeForPaymentTotalFinal: Int = 5
    lateinit var pd: ProgressDialog
    lateinit var defaultflag: String
    lateinit var defaultflag1: String
    private val COUNTRY_CODE_REQUEST = 113
    lateinit var myDialog: ProgressDialog
    lateinit var phone_code: ImageView;
    lateinit var iconSearch: ImageView;

    lateinit var mContext: Context;

    lateinit var viewHide1: LinearLayout;
    lateinit var viewHide2: LinearLayout;
    lateinit var viewAmount: LinearLayout;
    lateinit var payAmount: LinearLayout;
    lateinit var userNo: EditText;
    lateinit var enterAmount: EditText;
    lateinit var type:TextView;
    lateinit var pickingVehicle: Spinner
    lateinit var model: HomeLiveData
    var isPayScreenOpen = false
    var isCustomerSearch = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val rootView = inflater.inflate(R.layout.fragment_prebooking, container, false)

        mContext = rootView.context;
        instances2 = this
        type = rootView.findViewById(R.id.Type)
        pickingVehicle = rootView.findViewById<Spinner>(R.id.selection_value)
        phone_code = rootView.findViewById<ImageView>(R.id.phone_code)
        iconSearch = rootView.findViewById<ImageView>(R.id.icon_search)
        viewHide1 = rootView.findViewById<LinearLayout>(R.id.customer_records)
        viewHide2 = rootView.findViewById<LinearLayout>(R.id.no_customer_records)
        viewAmount = rootView.findViewById<LinearLayout>(R.id.amount_view)
        payAmount = rootView.findViewById<LinearLayout>(R.id.btn_payment)
        userNo = rootView.findViewById<EditText>(R.id.userNo)
        enterAmount = rootView.findViewById<EditText>(R.id.amount)

        val selCode = MAutoSharedPref.getAppPrefs(mContext).getStringValue("MAT_CODE")
        val selCountry = MAutoSharedPref.getAppPrefs(mContext).getStringValue("MAT_COUNTRY")
        defaultflag = "$selCode,$selCountry"
        defaultflag1 = selCountry

        pd = ProgressDialog(mContext)
        pd.setMessage("Loading...")
        myDialog = ProgressDialog(mContext)
        model = ViewModelProvider(this)[HomeLiveData::class.java]


        payAmount.setOnClickListener {

            if (enterAmount.text.toString().isNotEmpty()) {

                isPayScreenOpen = true
                MAutoSharedPref.getAppPrefs(mContext)
                    .saveStringValue(PrefConstant.MAT_PAY_AMOUNT, enterAmount.text.toString())
                getPayType()
            } else {
                ToastUtils.showToast(mContext, "Please enter the Booking Amount")
            }
        }
        val imageBuilder = StringBuilder()
        imageBuilder.append("flags/flag_").append(defaultflag1.lowercase(Locale.getDefault()))
            .append(".png")
        val inputStream = mContext.assets.open(imageBuilder.toString())
        val bitmap = BitmapFactory.decodeStream(inputStream)
        phone_code?.setImageBitmap(bitmap)
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
                viewAmount.visibility = View.GONE
                payAmount.visibility = View.GONE
                userNo.text.clear();
                if (model.clearCustomerData().hasObservers()) {
                    model.clearCustomerData().removeObservers(this)
                }
            }
        }
        phone_code.setOnClickListener {

            // countryIntent()
        }

        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
            phone_code?.setImageBitmap(bitmap)
            Log.d("PreBooking==>", "=$defaultflag")


        }
    }

    private fun getCustomer(number: String) {
        val split = defaultflag.split(",")
        //Declaring View model
        if (model == null)
            model = ViewModelProvider(this)[HomeLiveData::class.java]
        pd.show()
        //Calling API from Observer
        var isPlusCode = "+"
        MAutoSharedPref.getAppPrefs(mContext)
            .saveStringValue(PrefConstant.MAT_CUSTOMER_PHONE_CODE, "" + split[0])
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


    private fun getPayType() {

        if (model == null)
            model = ViewModelProvider(this)[HomeLiveData::class.java]
        pd.show()
        //Calling API from Observer

        model.getPayType()
            ?.observe(viewLifecycleOwner, Observer { this.setPayType(it) })


    }


    private fun setPayType(userStatus: PaymentTypeResponse?) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()

            if (model.clearDataPayType().hasObservers()) {
                model.clearDataPayType().removeObservers(this)
            }

            MAutoSharedPref.getAppPrefs(mContext)
                .savePayType(userStatus.response?.paymentMethod)

            val bottomSheet = BottomSheetDialog(this)

            if (isPayScreenOpen) {
                bottomSheet.show(
                    childFragmentManager,
                    "ModalBottomSheet"
                )
                isPayScreenOpen = false
            }

        } else {
            if (pd != null)
                pd.dismiss()

            if (model.clearDataPayType().hasObservers()) {
                model.clearDataPayType().removeObservers(this)
            }

            ToastUtils.showToast(mContext, "No Payment Option Available")
        }
    }

    private fun setStatus(userStatus: CustomerResponse?) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()

            if (model.clearCustomerData().hasObservers()) {
                model.clearCustomerData().removeObservers(this)
            }
            viewHide1.visibility = View.VISIBLE
            viewHide2.visibility = View.GONE
            viewAmount.visibility = View.VISIBLE
            payAmount.visibility = View.VISIBLE



            customerName.text = userStatus.response.name + " "
            //+ userStatus.response.surname
           // customerDetails.text = "ID : " + userStatus.response.leadId
            if(userStatus.response!!.type.equals("prebooking")){
                pickingVehicle.visibility=View.VISIBLE
            }
            else{
                pickingVehicle.visibility = View.GONE
            }
            Type.text=userStatus.response.type+""


            MAutoSharedPref.getAppPrefs(mContext)
                .saveStringValue(PrefConstant.MAT_CUSTOMER_ID, userStatus.response.id)

            val selectedModel = userStatus.response.selectedModel
            //val selectedModel = "PIAM"

            val modelList = userStatus.response.modelList

            var adapter1: ArrayAdapter<String> =
                ArrayAdapter<String>(
                    requireContext(),
                    R.layout.spinner_item, R.id.txt_cell,
                    modelList
                );

            pickingVehicle.adapter = adapter1

            if (selectedModel!!.isNotEmpty()) {

                pickingVehicle.setSelection(modelList.indexOf(selectedModel));
            }


            val toValue = pickingVehicle.selectedItem;

            MAutoSharedPref.getAppPrefs(mContext).saveStringValue(
                PrefConstant.MAT_CUSTOMER_VEHICLE,
                toValue as String?
            )


        } else {
            if (pd != null)
                pd.dismiss()

            if (model.clearCustomerData().hasObservers()) {
                model.clearCustomerData().removeObservers(this)
            }
            viewHide1.visibility = View.GONE
            viewHide2.visibility = View.VISIBLE
            viewAmount.visibility = View.GONE
            payAmount.visibility = View.GONE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    fun makePayment() {
        if (model == null)
            model = ViewModelProvider(this)[HomeLiveData::class.java]
        pd.show()
        //Calling API from Observer
        model.makePayment()
            ?.observe(viewLifecycleOwner, Observer { this.setPayStatus(it) })
    }


    private fun setPayStatus(userStatus: PaymentResponse?) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()
            if (model.clearDataMakePayment().hasObservers()) {
                model.clearDataMakePayment().removeObservers(this)
            }

            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).saveStringValue(
                PrefConstant.MAT_STATUS_UUID, userStatus.response!!.data!!.uuid
            )

            loadPaymentDialog()


        } else {
            if (pd != null)
                pd.dismiss()

            if (model.clearDataMakePayment().hasObservers()) {
                model.clearDataMakePayment().removeObservers(this)
            }

            ToastUtils.showToast(mContext, "Unable to make Payment")

        }
    }


    companion object {
        private var instances2: PreBookingFragment? = null
        fun getInstance(): PreBookingFragment? {
            return instances2
        }
    }

    fun reloadFragment() {
        val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false)
        }
        ft.detach(this).attach(this).commit()
    }


    fun loadDialog() {
        var cancel: TextView? = null
        //will create a view of our custom dialog layout
        val alertCustomdialog =
            LayoutInflater.from(mContext).inflate(R.layout.custom_view_success, null)
        //initialize alert builder.
        val alert: AlertDialog.Builder = AlertDialog.Builder(mContext)

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog)
        cancel = alertCustomdialog.findViewById(R.id.close)
        val dialog: AlertDialog = alert.create()
        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //finally show the dialog box in android all
        dialog.show()
        cancel.setOnClickListener {
            dialog.dismiss()
            viewHide1.visibility = View.GONE
            viewHide2.visibility = View.GONE
            viewAmount.visibility = View.GONE
            payAmount.visibility = View.GONE
            userNo.text.clear();
            HomePageActivity.getInstance()!!.reload()
        }

    }
    fun loadDialoging() {
        var cancel: TextView? = null
        //will create a view of our custom dialog layout
        val alertCustomdialog =
            LayoutInflater.from(mContext).inflate(R.layout.proccessing, null)
        //initialize alert builder.
        val alert: AlertDialog.Builder = AlertDialog.Builder(mContext)

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog)
        cancel = alertCustomdialog.findViewById(R.id.close)
        val dialog: AlertDialog = alert.create()
        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //finally show the dialog box in android all
        dialog.show()
        cancel.setOnClickListener {

            dialog.dismiss()
            removeObj()
            HomePageActivity.getInstance()!!.reload()
        }

    }


    fun loadPaymentDialog() {

        if (myDialog == null)
            myDialog = ProgressDialog(mContext)

        if (model == null)
            model = ViewModelProvider(this)[HomeLiveData::class.java]
        handler.postDelayed(Runnable {


            model.checkPayment()
                ?.observe(viewLifecycleOwner, Observer {

                    if (it!!.status.equals("1")) {

                        if (it.response!!.status.equals("Paid") || it.response!!.status.equals("paid")) {
                            removeObj()
                            handler.removeCallbacks(runnable!!)
                            handler.removeMessages(0)

                            if (myDialog != null)
                                myDialog.dismiss()
                            loadDialog()
                        } else if (it.response!!.status.equals("failed") || it.response!!.status.equals("Failed")) {
                            removeObj()
                            handler.removeCallbacks(runnable!!)
                            handler.removeMessages(0)

                            if (myDialog != null)
                                myDialog.dismiss()
                            ToastUtils.showToast(
                                mContext,
                                "Payment Failed. Please try later"
                            )
                            viewHide1.visibility = View.GONE
                            viewHide2.visibility = View.GONE
                            viewAmount.visibility = View.GONE
                            payAmount.visibility = View.GONE
                            userNo.text.clear();
                            HomePageActivity.getInstance()!!.reload()
                        }
                        else {
                           // Log.d("timeForPaymentTotal==", "" + timeForPaymentTotal)

                            if (timeForPaymentTotal >= timeForPaymentTotalFinal) {

                                removeObj()
                                handler.removeCallbacks(runnable!!)
                                handler.removeMessages(0)

                                if (myDialog != null)
                                    myDialog.dismiss()


                                ToastUtils.showToast(
                                    mContext,
                                    "Unable to confirm the Payment Status. Please try later"
                                )

                                viewHide1.visibility = View.GONE
                                viewHide2.visibility = View.GONE
                                viewAmount.visibility = View.GONE
                                payAmount.visibility = View.GONE
                                userNo.text.clear();
                                HomePageActivity.getInstance()!!.reload()

                            }
                        }

                    } else {

                        removeObj()
                        handler.removeCallbacks(runnable!!)
                        handler.removeMessages(0)

                        if (myDialog != null)
                            myDialog.dismiss()


                        ToastUtils.showToast(
                            mContext,
                            "Unable to make the Payment. Please try later"
                        )
                        viewHide1.visibility = View.GONE
                        viewHide2.visibility = View.GONE
                        viewAmount.visibility = View.GONE
                        payAmount.visibility = View.GONE
                        userNo.text.clear();
                        HomePageActivity.getInstance()!!.reload()
                    }
                })


            timeForPaymentTotal++
            handler.postDelayed(runnable!!, timeForPayment)


        }.also { runnable = it }, timeForPayment)


        myDialog.setTitle("Payment initiated, Please do not press back or switch to another app while we are payment processing");
        myDialog.setMessage("Loading...")
        myDialog.setCancelable(false)
        myDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            "Cancel"
        ) { dialogInterface, i ->
            //dismiss dialog
            removeObj()
            handler.removeCallbacks(runnable!!)
            handler.removeMessages(0)

            if (myDialog != null)
                myDialog.dismiss()
            viewHide1.visibility = View.GONE
            viewHide2.visibility = View.GONE
            viewAmount.visibility = View.GONE
            payAmount.visibility = View.GONE
            userNo.text.clear();
            HomePageActivity.getInstance()!!.reload()

        }
        loadDialoging()


    }


    fun removeObj() {
        if (model.clearPayStatus().hasObservers())
            model.clearPayStatus().removeObservers(this)
    }


}