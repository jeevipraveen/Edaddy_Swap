package com.mauto.myapplication.swappayment

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.mauto.myapplication.R
import com.mauto.myapplication.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_swapping_payment.*

class SwappingPaymentActivity : AppCompatActivity() {
    var paymenttype:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swapping_payment)


        var cash = findViewById<LinearLayout>(R.id.cash)
        var qr = findViewById<LinearLayout>(R.id.qr)
        var wallet = findViewById<LinearLayout>(R.id.wallet)
        var  swapamint = findViewById<TextView>(R.id.swapamint)
        var dis = findViewById<TextView>(R.id.dis)
        var remove = findViewById<TextView>(R.id.remove)
        val mString = "Swap amount"
        val mSpannableString = SpannableString(mString)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        swapamint.text = mSpannableString

        val mString2 = "Discount"
        val mSpannableString2 = SpannableString(mString2)
        mSpannableString2.setSpan(UnderlineSpan(), 0, mSpannableString2.length, 0)
        dis.text=mSpannableString2

        val mString3 = "Remove"
        val mSpannableString3 = SpannableString(mString3)
        mSpannableString3.setSpan(UnderlineSpan(), 0, mSpannableString3.length, 0)
        remove.text=mSpannableString3

        bac.setOnClickListener {
            finish()
        }

        cash.setOnClickListener{
            ok_img.visibility = View.VISIBLE
            select_img.visibility=View.GONE
            ok_img_qr.visibility=View.GONE
            ok_img_wallet.visibility=View.GONE
            select_img2.visibility = View.VISIBLE
            select_img3.visibility = View.VISIBLE
            paymenttype="cash"
        }

        qr.setOnClickListener{

            ok_img_qr.visibility=View.VISIBLE
            ok_img.visibility = View.GONE
            select_img2.visibility = View.GONE
            ok_img_wallet.visibility=View.GONE
            select_img.visibility = View.VISIBLE
            select_img3.visibility = View.VISIBLE
            paymenttype="qr"
        }
        wallet.setOnClickListener{
            ok_img_wallet.visibility=View.VISIBLE
            ok_img_qr.visibility=View.GONE
            ok_img.visibility = View.GONE
            select_img3.visibility = View.GONE
            select_img2.visibility = View.VISIBLE
            select_img.visibility = View.VISIBLE
            paymenttype="wallet"
        }
        wallet_add.setOnClickListener {
            if (paymenttype.isNotEmpty()) {
                var i = Intent(this@SwappingPaymentActivity, PaymentWalletActivity::class.java)
                startActivity(i)
            }else{
                ToastUtils.showToast(applicationContext, "Please select payment option")

            }
        }

        pay.setOnClickListener {
            if (paymenttype.equals("cash")) {
                cashsucessDilog()
                ToastUtils.showToast(applicationContext, "cash")
            }else if (paymenttype.equals("qr")){

                ToastUtils.showToast(applicationContext, "qr")
            }else if (paymenttype.equals("wallet")){
                ToastUtils.showToast(applicationContext, "Wallet")

            }else{
                ToastUtils.showToast(applicationContext, "Please select payment option")

            }
        }
    }
    fun cashsucessDilog(){
        val dialog = Dialog(this, android.R.style. Theme_Light_NoTitleBar_Fullscreen )
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.payment_confarmection)
        dialog.window!!.setGravity(Gravity.CENTER)
        val displayMetrics = DisplayMetrics()
        var cancle =dialog.findViewById<LinearLayout>(R.id.cancle)

        var cuntryname2 =dialog.findViewById<LinearLayout>(R.id.cuntryname2)
        cuntryname2.setOnClickListener {
            paymentSuccess()
            dialog.cancel()
        }

        cancle.setOnClickListener{
            dialog.cancel()
        }
        dialog.show()
    }


    fun paymentSuccess(){
        val dialog = Dialog(this, android.R.style. Theme_Light_NoTitleBar_Fullscreen )
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.paymentsuceess)
        dialog.window!!.setGravity(Gravity.CENTER)
        val displayMetrics = DisplayMetrics()
        var done =dialog.findViewById<CardView>(R.id.done)
//
//
        done.setOnClickListener{
            dialog.cancel()
        }
        dialog.show()
    }

}