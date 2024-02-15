package com.mauto.myapplication.profile

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mauto.myapplication.R
import kotlinx.android.synthetic.main.booking_history.close_view


class AboutActivity : AppCompatActivity() {


    lateinit var pd: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)


        close_view.setOnClickListener {

            finish()
        }


    }


}

