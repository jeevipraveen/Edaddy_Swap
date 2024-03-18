package com.mauto.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mauto.myapplication.home.HomePageActivity
import com.mauto.myapplication.login.LoginActivity
import com.mauto.myapplication.utils.PrefConstant
import com.mauto.myapplication.utils.MAutoApplication
import com.mauto.myapplication.utils.MAutoSharedPref


class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Setting Delay for splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            val sCache =
                MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context)
                    .getBooleanValue(PrefConstant.LOGIN_STATUS)

            Log.d("jhdbfjkb","$sCache")
            if (sCache)
                loginFromSession()
            else
                newLogin()
        }, 3000)

//        Glide.with(this).load(R.drawable.splach).into(image_splash);

    }

    //If previous login session available
    private fun loginFromSession() {
        val i = Intent(this@SplashActivity, DashboardActivity::class.java)
        startActivity(i)
        finish()
    }

    //If no previous login session available
    private fun newLogin() {
        val i = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(i)
        finish()
    }
}