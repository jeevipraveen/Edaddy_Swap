package com.mauto.myapplication.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mauto.myapplication.DashboardActivity
import com.mauto.myapplication.R
import com.mauto.myapplication.login.livedata.LoginLiveData
import com.mauto.myapplication.login.model.*
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.PrefConstant
import com.mauto.myapplication.utils.ToastUtils
import kotlinx.android.synthetic.main.otp_verification.*


class OtpActivity : AppCompatActivity() {


    lateinit var pd: ProgressDialog

    private var stringMessage: String = ""
    private var stringCode: String = ""
    private var stringMobile: String = ""
    private var stringCountry: String = ""
    private var verification_code: String = ""
    var resend_count: String = ""
    lateinit var model: LoginLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_verification)

        pd = ProgressDialog(this@OtpActivity)
        pd.setMessage("Loading...")

        model = ViewModelProvider(this)[LoginLiveData::class.java]
        var loginEnter = findViewById<LinearLayout>(R.id.nextlayout)
        var edit_mobile = findViewById<ImageView>(R.id.edit_mobile)
        var btn_login1 = findViewById<CardView>(R.id.btn_login1)
        verfication()

        btn_login1.setOnClickListener {
            verfication()
            var i = Intent(this@OtpActivity,DashboardActivity::class.java)
            startActivity(i)
        }
        edit_mobile.setOnClickListener{
            val i = Intent(this@OtpActivity,LoginActivity ::class.java)
            startActivity(i)
        }



        stringMessage =
            MAutoSharedPref.getAppPrefs(applicationContext).getStringValue("MAT_OTP_MSG")

        stringCode =
            MAutoSharedPref.getAppPrefs(applicationContext).getStringValue("MAT_CODE")
        stringMobile =
            MAutoSharedPref.getAppPrefs(applicationContext).getStringValue("MAT_PHONE")
        stringCountry =
            MAutoSharedPref.getAppPrefs(applicationContext).getStringValue("MAT_COUNTRY")
        var stringOTP =
            MAutoSharedPref.getAppPrefs(applicationContext).getStringValue("MAT_OTP_NO")


        setOTPtoScreen(stringOTP.toString())

        mobile.setText(MAutoSharedPref.getAppPrefs(applicationContext).getStringValue("MAT_PHONE"))

        enter_your_phone.text = stringCode + stringMobile


        resendcode.text = ""

        object : CountDownTimer(30000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                resendcode.text = "Seconds remaining: " + millisUntilFinished / 1000
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                resendcode.text = "Resend OTP"
            }
        }.start()


        loginEnter.setOnClickListener {

            var stringOTP =
                MAutoSharedPref.getAppPrefs(applicationContext).getStringValue("MAT_OTP_NO")

            var otpEntered: String =
                otp1.text.toString() + otp2.text.toString() + otp3.text.toString() + otp4.text.toString()

            if (otpEntered == stringOTP) {
                loginSuccess()
            } else {
                ToastUtils.showToast(applicationContext, "Invalid OTP")
            }

        }

        resendcode.setOnClickListener {
            if (resendcode.text == "Resend OTP")
                if (resend_count.equals("")){
                    resend_count ="1"
                    resend_count_ok.setText("("+resend_count+"/3"+")")
                    contre()
                }else if (resend_count.equals("1")) {
                    resend_count = "2"
                    resend_count_ok.setText("("+resend_count+"/3"+")")
                    contre()
                }else if (resend_count.equals("2")){
                    resend_count = "3"
                    resend_count_ok.setText("("+resend_count+"/3"+")")
                    contre()
                }

                getOTP()
        }


        back.setOnClickListener {


            val i = Intent(this@OtpActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }




        otp1.doOnTextChanged { text, start, count, after ->
            if (text!!.length == 1) {
                otp2.requestFocus()
            }
        }

        otp2.doOnTextChanged { text, start, count, after ->
            if (text!!.length == 1) {
                otp3.requestFocus()
            }
        }

        otp3.doOnTextChanged { text, start, count, after ->
            if (text!!.length == 1) {
                otp4.requestFocus()
            }
        }


        otp4.doOnTextChanged { text, start, count, after ->
            if (text!!.length == 1) {

            }
        }


    }
    fun contre(){
        object : CountDownTimer(30000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                resendcode.text = "Seconds remaining: " + millisUntilFinished / 1000
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                resendcode.text = "Resend OTP"
            }
        }.start()

    }
    private fun verfication(){

        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        var cuntery_code=
            MAutoSharedPref.getAppPrefs(applicationContext).getStringValue("MAT_CODE")
        var mobilenumber =
            MAutoSharedPref.getAppPrefs(applicationContext).getStringValue("MAT_PHONE")
        var dail_dode =
            MAutoSharedPref.getAppPrefs(applicationContext).getStringValue("MAT_COUNTRY")

        var request: LoginSwapRequestOTP = LoginSwapRequestOTP(dail_dode, mobilenumber,cuntery_code )


        //Declaring View model
        if (model == null)
            model = ViewModelProvider(this)[LoginLiveData::class.java]
        pd.show()
        //Calling API from Observer
        model.swapOTP(request)
            ?.observe(this, Observer { this.setSwapUserStatus(it) })


    }


    private fun getOTP() {
        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //Declaring View model
        if (model == null)
            model = ViewModelProvider(this)[LoginLiveData::class.java]
        pd.show()
//        var request: OTPRequest = OTPRequest(stringCode, stringMobile, stringCountry,verification_code)
        //Calling API from Observer
//        model.getMyOTP(request)
//            ?.observe(this, Observer { this.setOTPStatus(it) })


    }

    private fun setOTPStatus(userStatus: OTPResponse?) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()

            MAutoSharedPref.getAppPrefs(applicationContext).saveStringValue(
                "MAT_OTP_MSG",
                userStatus.response!!.message
            )
            MAutoSharedPref.getAppPrefs(applicationContext).saveStringValue(
                "MAT_OTP_NO",
                userStatus.response!!.otp
            )


            val otp = userStatus.response!!.otp

            setOTPtoScreen(otp.toString())
            if (model.clearOTPData().hasObservers()) {
                model.clearOTPData().removeObservers(this)
            }   //  reLoad()


        } else {
            if (pd != null)
                pd.dismiss()
            //  reLoad()
            if (model.clearOTPData().hasObservers()) {
                model.clearOTPData().removeObservers(this)
            }
            ToastUtils.showToast(applicationContext, "Unable to sent OTP")
        }
    }


    private fun reLoad() {
        val intent = intent
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }


    private fun loginSuccess() {
        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        var stringOTP =
            MAutoSharedPref.getAppPrefs(applicationContext).getStringValue("MAT_OTP_NO")

        var request: LoginRequestOTP = LoginRequestOTP(stringCode, stringMobile, stringOTP)

        //Declaring View model
        if (model == null)
            model = ViewModelProvider(this)[LoginLiveData::class.java]
        pd.show()
        //Calling API from Observer
        model.loginByOTP(request)
            ?.observe(this, Observer { this.setUserStatus(it) })


    }


    private fun setUserStatus(userStatus: LoginResponse?) {
        if (userStatus!!.status.equals("1")) {
            val profile = userStatus.response!!.profile

            MAutoSharedPref.getAppPrefs(applicationContext).saveStringValue(
                PrefConstant.SECRET_TOKEN,
                profile!!.staffId
            )

            MAutoSharedPref.getAppPrefs(applicationContext).saveProfileInfo(profile)
            MAutoSharedPref.getAppPrefs(applicationContext).saveBooleanValue(
                PrefConstant.LOGIN_STATUS,
                true
            )

            if (pd != null)
                pd.dismiss()
            val i = Intent(this@OtpActivity, DashboardActivity::class.java)
            startActivity(i)
            finish()


        } else {
            if (pd != null)
                pd.dismiss()
            //   reLoad()
            ToastUtils.showToast(applicationContext, "UnAuthorised User")
        }
    }


    private fun setSwapUserStatus(userStatus: SwapLocationResponse?) {
        if (userStatus!!.status.equals("1")) {

//            MAutoSharedPref.getAppPrefs(applicationContext).saveStringValue(
//                PrefConstant.SECRET_TOKEN,
////                profile!!.staffId
//            )
//
//            MAutoSharedPref.getAppPrefs(applicationContext).saveProfileInfo(profile)
//            MAutoSharedPref.getAppPrefs(applicationContext).saveBooleanValue(
//                PrefConstant.LOGIN_STATUS,
//                true
//            )

            if (pd != null)
                pd.dismiss()
//            val i = Intent(this@OtpActivity, DashboardActivity::class.java)
//            startActivity(i)
//            finish()


        }
        else if(userStatus!!.status.equals("0")) {
            if (pd != null)
                pd.dismiss()


        }else {
            if (pd != null)
                pd.dismiss()
            //   reLoad()
//            ToastUtils.showToast(applicationContext, "UnAuthorised User")
        }
    }







//    private fun setOTPtoScreen(otp: String) {
//        if (BuildConfig.DEBUG) {
//            if (otp!!.length == 4) {
//                otp1.setText(otp[0].toString())
//                otp2.setText(otp[1].toString())
//                otp3.setText(otp[2].toString())
//                otp4.setText(otp[3].toString())
//            }
//        }
//
//    }

    private fun setOTPtoScreen(otp: String) {
       // if (BuildConfig.DEBUG) {
            if (otp!!.length == 4) {
                otp1.setText(otp[0].toString())
                otp2.setText(otp[1].toString())
                otp3.setText(otp[2].toString())
                otp4.setText(otp[3].toString())
            }
      // }

    }
}

