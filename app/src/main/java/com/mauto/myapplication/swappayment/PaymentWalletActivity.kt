package com.mauto.myapplication.swappayment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mauto.myapplication.R
import kotlinx.android.synthetic.main.activity_payment_wallet.*

class PaymentWalletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_wallet)

        bac.setOnClickListener {  finish() }

        btn_login1.setOnClickListener {  }
    }
}