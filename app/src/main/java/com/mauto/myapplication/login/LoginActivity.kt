package com.mauto.myapplication.login

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.mauto.myapplication.home.CountryCodeSelection
import com.mauto.myapplication.home.CountryCodeViewModel
import com.mauto.myapplication.login.livedata.LoginLiveData
import com.mauto.myapplication.login.model.*
import com.mauto.myapplication.utils.ApiConstant
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.PrefConstant
import com.mauto.myapplication.utils.ToastUtils
import com.mauto.myapplication.R
import com.mauto.myapplication.api.error.NetworkError
import com.mauto.myapplication.home.adapter.LocationAdapter

import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {
    lateinit var pd: ProgressDialog
    private lateinit var toUser: String
    lateinit var tPassword: String
    private lateinit var mContext: Activity
    var defaultflag: String = "91,IN"
    var defaultflag1: String = "IN"
    var cuntery_code:String="91"
    private val COUNTRY_CODE_REQUEST = 113
    private val RESULT_PAYMENT_METHOD: Int = 1001
    var country_name = "TG"
    lateinit var model :LoginLiveData
    lateinit var mViewModel: CountryCodeViewModel
    var curent_lat:String=""
    var station_id:String="0"
    var curent_lagt:String=""
    var verification_code:String="986548"
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        var loginEnter = findViewById<TextView>(R.id.btn_login)
        var btn_login1 = findViewById<CardView>(R.id.btn_login1)
        var cuntry=findViewById<TextView>(R.id.cuntry)


        instances2 = this;
        mContext = this@LoginActivity
        pd = ProgressDialog(this@LoginActivity)
        pd.setMessage("Loading...")


        if (NetworkError.isNetworkAvailable(mContext)) {
            getLocation()


        }else{

            ToastUtils.showToast(applicationContext, "Please check your net work connection ")

        }
         model = ViewModelProvider(this)[LoginLiveData::class.java]

         btn_login1.setOnClickListener {
             toUser = mobileNo.text.toString()
             station_id = MAutoSharedPref.getAppPrefs(mContext).getStringValue(PrefConstant.MAT_STACTION_ID)
             if ((toUser != null && toUser.isNotEmpty())&&(verification_code !=null && verification_code.isNotEmpty())) {


                 getOTP(toUser,verification_code,station_id,curent_lat,curent_lagt)

             } else {
                 ToastUtils.showToast(applicationContext, "Please enter Mobile Number")
             }
         }

        loginEnter.setOnClickListener {
            toUser = username.text.toString()
            tPassword = password.text.toString()


            if (login_with_password.isVisible) {
//                if ((toUser != null && toUser.isNotEmpty())) {
//                   getOTP(toUser)
//                if ((toUser != null && toUser.isNotEmpty())&&(verification_code !=null && verification_code.isNotEmpty())) {
//                    getOTP(toUser,verification_code)
//                } else {
                    ToastUtils.showToast(applicationContext, "Please enter Mobile Number")
//                }
            } else {
                if ((toUser != null && toUser.isNotEmpty()) && (tPassword != null && tPassword.isNotEmpty())) {
//                    loginSuccess(toUser, tPassword)

                } else {
                    ToastUtils.showToast(
                        applicationContext,
                        "Please enter Mobile Number and Password"
                    )
                }
            }


        }

        phone_code.setOnClickListener {

            countryIntent()
        }

        cuntry.setOnClickListener {

            countryIntent()
        }

        cuntry_select.setOnClickListener{
            showCustomDialog()
        }



//        login_with_password.setOnClickListener {
//            hide_password.visibility = View.VISIBLE
//            login_with_password.visibility = View.GONE
//            back.visibility = View.VISIBLE
//            login_with_otp.visibility = View.VISIBLE
//            btn_login.text = "Login"
//        }




        login_with_otp.setOnClickListener {
            hide_password.visibility = View.GONE
            login_with_password.visibility = View.VISIBLE
            back.visibility = View.GONE
            login_with_otp.visibility = View.GONE
            btn_login.text = "Login with OTP"
        }


        back.setOnClickListener {
            hide_password.visibility = View.GONE
            login_with_password.visibility = View.VISIBLE
            back.visibility = View.GONE
            login_with_otp.visibility = View.GONE
            btn_login.text = "Login with OTP"
        }


        val imageBuilder = StringBuilder()
        imageBuilder.append("flags/flag_").append(defaultflag1.lowercase(Locale.getDefault())).append(".png")
        val inputStream = mContext.assets.open(imageBuilder.toString())
        val bitmap = BitmapFactory.decodeStream(inputStream)
        phone_code?.setImageBitmap(bitmap)
        cuntry.setText("+"+cuntery_code)


    }
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun showCustomDialog() {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.swapstatios_dialog)
        dialog.window!!.setGravity(Gravity.CENTER)
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthLcl = (displayMetrics.widthPixels * 0.9f)
        val heightLcl = WindowManager.LayoutParams.WRAP_CONTENT
        var swap_lay=dialog.findViewById<LinearLayout>(R.id.swap_lay)
        val paramsLcl = swap_lay.getLayoutParams() as FrameLayout.LayoutParams
        paramsLcl.width = widthLcl.toInt()
        paramsLcl.height = heightLcl.toInt()
        paramsLcl.gravity = Gravity.CENTER_VERTICAL
        val window: Window = dialog.getWindow()!!
        swap_lay.setLayoutParams(paramsLcl)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Accessing views from the layout
        val dialogTitle = dialog.findViewById<TextView>(R.id.dialogTitle)
        val closeButton = dialog.findViewById<TextView>(R.id.colse)
        val recyclerView =dialog .findViewById<RecyclerView>(R.id.list)
        val Select = dialog.findViewById<TextView>(R.id.Select)


        val payType = MAutoSharedPref.getAppPrefs(mContext).getDarction()
        val Id=  MAutoSharedPref.getAppPrefs(mContext).getStringValue(PrefConstant.MAT_STACTION_ID)
        Log.d("bfkhehbdkjnalMD","$Id")


        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))

        var adapter = LocationAdapter(mContext, payType!!)
        recyclerView.adapter = adapter
        // Set your custom title
        dialogTitle.text = "Custom Dialog Title"
        val stringValue =
            MAutoSharedPref.getAppPrefs(mContext).getStringValue(PrefConstant.MAT_PAY_METHOD);

        // Set click listener for the close button
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        Select.setOnClickListener{
            cuntryname.setText(stringValue)
            dialog.dismiss()
        }

        dialog.show()

    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1) as List<Address>
                        tvLatitude.text = "${list[0].latitude}"
                        tvLongitude.text = "${list[0].longitude}"
                        curent_lat = tvLatitude.text.toString()
                        curent_lagt= tvLongitude.text.toString()
                        Log.d("jgfhgd","$curent_lat")
//                        locationfinder(curent_lat,curent_lat)
                        MAutoSharedPref.getAppPrefs(applicationContext).saveStringValue(PrefConstant.CURENT_LAT,curent_lat)
                        MAutoSharedPref.getAppPrefs(applicationContext).saveStringValue(PrefConstant.CURENT_LANG,curent_lagt)
                        loginSuccess(curent_lat, curent_lagt)



//                        tvCountryName.text = "Country Name\n${list[0].countryName}"
//                        tvLocality.text = "Locality\n${list[0].locality}"
//                        tvAddress.text = "Address\n${list[0].getAddressLine(0)}"

                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }


    private fun countryIntent() {
        val intentBookNow = Intent(mContext, CountryCodeSelection::class.java)
        startActivityForResult(intentBookNow, COUNTRY_CODE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_FIRST_USER) {


            defaultflag = data?.getStringExtra(ApiConstant.INTENT_OBJECT).toString()
            defaultflag1 = data?.getStringExtra(ApiConstant.INTENT_OBJECT1).toString()
            cuntery_code=data?.getStringExtra(ApiConstant.INTENT_CUNTYCODE).toString()
            val imageBuilder = StringBuilder()
            imageBuilder.append("flags/flag_").append(defaultflag1.toLowerCase()).append(".png")
            val inputStream = mContext.assets.open(imageBuilder.toString())
            val bitmap = BitmapFactory.decodeStream(inputStream)
            cuntry.setText("+"+cuntery_code)
            Log.d("Login==>", "=$defaultflag")


        }
    }


    private fun loginSuccess(curent_lat: String, curent_lagt: String) {
        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val split = defaultflag.split(",")
        var request: LoginRequest = LoginRequest(curent_lat, curent_lagt)

        //Declaring View model
        if(model==null)
         model = ViewModelProvider(this)[LoginLiveData::class.java]
        pd.show()
        //Calling API from Observer
        model.loginlocation(request)
            ?.observe(this, Observer { this.setUserStatus(it) })
//            ?.observe(this, Observer { this.setUserStatus(it) })


    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }



//    private fun locationfinder(number: String,number2: String){
//        try {
//            val imm: InputMethodManager =
//                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//          val split = defaultflag.split(",")
//        var request: LoginRequest = LoginRequest(split[0], number, password)
//    }
    private fun getOTP(number: String,verification_code:String,stationid:String,lat:String,lng:String) {
        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val split = defaultflag.split(",")
        var request: OTPRequest = OTPRequest("+"+split[0],number,split[1],verification_code,stationid,lat,lng)

        MAutoSharedPref.getAppPrefs(applicationContext).saveStringValue("MAT_CODE","+"+split[0])
        MAutoSharedPref.getAppPrefs(applicationContext).saveStringValue("MAT_PHONE",number)
        MAutoSharedPref.getAppPrefs(applicationContext).saveStringValue("MAT_COUNTRY",split[1])

        //Declaring View model
        if(model==null)
            model = ViewModelProvider(this)[LoginLiveData::class.java]
        pd.show()
        //Calling API from Observer
        model.getMyOTP(request)
            ?.observe(this, Observer { this.setOTPStatus(it) })


    }

//    private fun setUserStatus(userStatus: LoginResponse?)
    private fun setUserStatus(userStatus: LocationResponse?){
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()


//            val profile = userStatus.response!!.profile
//
//            MAutoSharedPref.getAppPrefs(applicationContext).saveStringValue(PrefConstant.SECRET_TOKEN,
//                profile!!.staffId)
//
//            MAutoSharedPref.getAppPrefs(applicationContext).saveProfileInfo(profile)
//
            PrefConstant.LOGIN_STATUS
            MAutoSharedPref.getAppPrefs(applicationContext).saveBooleanValue(PrefConstant.LOGIN_STATUS,
                true)
//

            MAutoSharedPref.getStactionList(mContext).saveLocationlist(userStatus?.response)




        } else  if (userStatus!!.status.equals("0")){
            ToastUtils.showToast(applicationContext, "Error")
        }
        else {
            if (pd != null)
                pd.dismiss()
            MAutoSharedPref.getAppPrefs(applicationContext).saveBooleanValue(
                PrefConstant.LOGIN_STATUS,
                false)

//            if (model.clearLoginData().hasObservers()) {
//                model.clearLoginData().removeObservers(this)
//            }
         //   reLoad()
            ToastUtils.showToast(applicationContext, "Error")
        }
    }



    private fun setOTPStatus(userStatus: OTPResponse?) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()

            if (model.clearOTPData().hasObservers()) {
                model.clearOTPData().removeObservers(this)
            }

            MAutoSharedPref.getAppPrefs(this).saveStringValue(PrefConstant.TOKEN, userStatus!!.response!!.token)



            val i = Intent(this@LoginActivity, OtpActivity::class.java)
            startActivity(i)


        } else if(userStatus!!.status.equals("0")){
            if (pd != null)
                pd.dismiss()
            ToastUtils.showToast(applicationContext, "UnAuthorised Mobile Number")



        }

        else {
            if (pd != null)
                pd.dismiss()
            if (model.clearOTPData().hasObservers()) {
                model.clearOTPData().removeObservers(this)
            }

            ToastUtils.showToast(applicationContext, "UnAuthorised Mobile Number")
        }
    }


    private fun reLoad() {
        val intent = intent
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }
    override fun onResume() {
        super.onResume()
        if (NetworkError.isNetworkAvailable(mContext)) {
            getLocation()


        }else{

            ToastUtils.showToast(applicationContext, "Please check your net work connection ")

        }

    }

    companion object {
        private var instances2: LoginActivity? = null
        fun getInstance(): LoginActivity? {
            return instances2
        }
    }

}

