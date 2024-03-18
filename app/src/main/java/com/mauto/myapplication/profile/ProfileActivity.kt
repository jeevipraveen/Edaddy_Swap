package com.mauto.myapplication.profile

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mauto.myapplication.DashboardActivity
import com.mauto.myapplication.R
import com.mauto.myapplication.SwapStactionActivity
import com.mauto.myapplication.batteryscanning.BatterySwappingActivity
import com.mauto.myapplication.home.model.ProfileResponce
import com.mauto.myapplication.login.LoginActivity
import com.mauto.myapplication.login.livedata.LoginLiveData
import com.mauto.myapplication.swapingHistory.SwapingHisttoryActivity
import com.mauto.myapplication.utils.MAutoApplication
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.PrefConstant
import com.mauto.myapplication.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.batterswap3
import kotlinx.android.synthetic.main.activity_profile.dashboard
import kotlinx.android.synthetic.main.activity_profile.map
import kotlinx.android.synthetic.main.activity_profile.profile
import kotlinx.android.synthetic.main.activity_profile.swap_history


class ProfileActivity : AppCompatActivity() {

    var SELECT_PICTURE = 200
    private lateinit var model: LoginLiveData
    lateinit var pd: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        model = ViewModelProvider(this)[LoginLiveData::class.java]
        pd = ProgressDialog(this)
        pd.setMessage("Loading...")
        map.setOnClickListener{
            var i = Intent(this, SwapStactionActivity::class.java)
            startActivity(i)
        }
        dashboard.setOnClickListener {
            var i = Intent(this, DashboardActivity::class.java)
            startActivity(i)
        }
        batterswap3.setOnClickListener {
            var i = Intent(this, BatterySwappingActivity::class.java)
            startActivity(i)
        }
        profile.setOnClickListener {
            var i = Intent(this, ProfileActivity::class.java)
            startActivity(i)
        }
        swap_history.setOnClickListener {
            var i = Intent(this, SwapingHisttoryActivity::class.java)
            startActivity(i)
        }
        logoutFromApp()

        logut.setOnClickListener {
            MAutoSharedPref.getAppPrefs(
                MAutoApplication.getInstance().applicationContext
            ).clearAllSharedPrefs()

//            MAutoSharedPref.getAppPrefs(
//                MAutoApplication.getInstance().applicationContext
//            ). clearSharedPrefs()

            ToastUtils.showToast(applicationContext, "Logged out successfully")
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()


        }


//        val profileInfo = MAutoSharedPref.getAppPrefs(applicationContext).profileInfo
//        pd = ProgressDialog(this@ProfileActivity)
//        pd.setMessage("Loading...")
//
//        username.text = profileInfo!!.name
//        emailID.text = profileInfo!!.email
//        mobileNo.text = profileInfo!!.phoneNumber
//
//        val options: RequestOptions = RequestOptions()
//            .centerCrop()
//            .placeholder(R.drawable.user_profiles)
//            .error(R.drawable.user_profiles)
//
//        Glide.with(this).load(profileInfo.image).apply(options).into(profile_img);
//
//
//
//        close_view.setOnClickListener {
//
//            finish()
//        }
//
//
//        profile_img.setOnClickListener{
//
//            imageChooser()
//        }





    }
    private fun logoutFromApp() {
        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        pd.show()
//        Calling API from Observer

        if (model == null)
            model = ViewModelProvider(this)[LoginLiveData::class.java]

        model.profile()
            ?.observe(this, Observer { this.setStatus(it) })
//

    }


    private fun setStatus(userStatus: ProfileResponce) {

        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()
            name.text= userStatus.response.profile.agent_name
            email.text= userStatus.response.profile.email

        }else{
            if (pd != null)
                pd.dismiss()
        }
    }


//    fun imageChooser() {
//
//        // create an instance of the
//        // intent of the type image
//        val i = Intent()
//        i.type = "image/*"
//        i.action = Intent.ACTION_GET_CONTENT
//
//        // pass the constant to compare it
//        // with the returned requestCode
//        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
//    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//
//            // compare the resultCode with the
//            // SELECT_PICTURE constant
//            if (requestCode == SELECT_PICTURE) {
//                // Get the url of the image from data
//                val selectedImageUri: Uri? = data!!.data
//                if (null != selectedImageUri) {
//                    // update the preview image in the layout
//                   var myFile= File(selectedImageUri.path);
//                    uploadPhoto(myFile)
//                }
//            }
//        }
//    }

//    private fun uploadPhoto(myFile:File) {
//        try {
//            val imm: InputMethodManager =
//                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        val model = ViewModelProvider(this)[HomeLiveData::class.java]
//        pd.show()
//        //Calling API from Observer
//        model.uploadProfileImage(username.text.toString(),myFile)
//            ?.observe(this, Observer { this.setStatus(it) })
//
//
//    }

//    private fun setStatus(userStatus: CancelPaymentResponse?) {
//        if (userStatus!!.status.equals("1")) {
//            if (pd != null)
//                pd.dismiss()
//
//            MAutoSharedPref.getAppPrefs(
//                MAutoApplication.getInstance().applicationContext
//            ).clearAllSharedPrefs()
//
//            ToastUtils.showToast(applicationContext, "Profile Image Updated Successfully")
//
//        } else {
//            if (pd != null)
//                pd.dismiss()
//
//            ToastUtils.showToast(applicationContext, "Unable to Update Profile Image")
//        }
//
//    }



}

