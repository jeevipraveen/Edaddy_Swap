package com.mauto.myapplication.utils


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.mauto.myapplication.R
import com.mauto.myapplication.swapingHistory.adapter.SwapHistoryAdapter


class ToastUtils {


    companion object {


        fun showToast(context: Context, message: String?) {
            val toast = Toast(context)
            val view: View = LayoutInflater.from(context)
                .inflate(R.layout.toast_layout, null)
            val tvMessage: TextView = view.findViewById(R.id.tvMessage)
            tvMessage.text = message
            toast.view = view
            toast.show()
        }
    }
}