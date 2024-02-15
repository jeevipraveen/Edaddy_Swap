package com.mauto.myapplication.home

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mauto.myapplication.R
import com.mauto.myapplication.home.adapter.PayTypeAdapter
import com.mauto.myapplication.utils.MAutoApplication
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.PrefConstant
import com.mauto.myapplication.utils.ToastUtils

class BottomSheetDialog(PreBookingFragment: PreBookingFragment) : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        val v = inflater.inflate(R.layout.bottom_sheet_layout,
            container, false
        )


        val payType = MAutoSharedPref.getAppPrefs(v.context).payType

        MAutoSharedPref.getAppPrefs(v.context).saveStringValue(PrefConstant.MAT_PAY_METHOD, "");
        val stringValue =
            MAutoSharedPref.getAppPrefs(v.context).getStringValue(PrefConstant.MAT_PAY_AMOUNT);
        val paytheAmount = v.findViewById<LinearLayout>(R.id.continue_button)
        val recyclerView = v.findViewById<RecyclerView>(R.id.recycler_view)
        val closeView = v.findViewById<ImageView>(R.id.close_view)
        val amount = v.findViewById<TextView>(R.id.amount_to_pay)

        amount.text = stringValue


        recyclerView.layoutManager = LinearLayoutManager(v.context)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))


        var adapter = PayTypeAdapter(v.context, payType!!)
        recyclerView.adapter = adapter

        closeView.setOnClickListener {

            dismiss()
        }


        paytheAmount.setOnClickListener {

            val stringValue =
                MAutoSharedPref.getAppPrefs(v.context).getStringValue(PrefConstant.MAT_PAY_METHOD);

            if (stringValue.equals("tmoney") || stringValue.equals("moovtg")) {
                MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context)
                    .saveStringValue(
                        PrefConstant.MAT_CUSTOMER_PHONE_CODE, "+228"
                    )
            }


            if (stringValue.isEmpty()) {
                ToastUtils.showToast(v.context, "Please select payment type")
            } else {
                PreBookingFragment.Companion.getInstance()!!.makePayment()
                // ToastUtils.showToast(requireActivity(), "Payment Clicked")
                dismiss()
            }
        }



        return v
    }
}