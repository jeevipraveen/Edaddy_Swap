package com.mauto.myapplication.login.livedata


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauto.myapplication.api.ApiServices
import com.mauto.myapplication.api.RetrofitBuilder
import com.mauto.myapplication.api.error.NetworkState
import com.mauto.myapplication.home.dashModel.DashboardResponce
import com.mauto.myapplication.home.model.ProfileResponce
import com.mauto.myapplication.login.model.*
import com.mauto.myapplication.utils.ApiConstant
import com.mauto.myapplication.utils.MAutoApplication
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.PrefConstant
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class LoginLiveData : ViewModel() {
    private var liveDataLogin: MutableLiveData<LoginResponse>? = null
    private var liveDataOTP: MutableLiveData<OTPResponse>? = null

    private var liveDraction: MutableLiveData<LocationResponse>? = null
    private var liveDashBoard: MutableLiveData<DashboardResponce>? = null
    private var liveProfile: MutableLiveData<ProfileResponce>? = null

    private  var liveOTPverfication:MutableLiveData<SwapLocationResponse>? = null

    private val mState = MutableLiveData<NetworkState>()


    internal fun loginlocation(
        request: LoginRequest
    ): MutableLiveData<LocationResponse> {
        if (liveDraction == null) {
            liveDraction = MutableLiveData()
        }
        loginByLocation(request)
        return liveDraction as MutableLiveData<LocationResponse>
    }

    // swap otp hit
    internal fun swapOTP(
        request: LoginSwapRequestOTP
    ): MutableLiveData<SwapLocationResponse> {
        if (liveOTPverfication == null) {
            liveOTPverfication = MutableLiveData()
        }
        otpverification(request)
        return liveOTPverfication as MutableLiveData<SwapLocationResponse>
    }

    internal fun dash(

    ): MutableLiveData<DashboardResponce> {
        if (liveDashBoard == null) {
            liveDashBoard = MutableLiveData()
        }
        dashBordApi()
        return liveDashBoard as MutableLiveData<DashboardResponce>
    }
    internal fun profile(

    ): MutableLiveData<ProfileResponce> {
        if (liveProfile == null) {
            liveProfile = MutableLiveData()
        }
        profileApi()
        return liveProfile as MutableLiveData<ProfileResponce>
    }



    internal fun loginByPassword(
        request: LoginRequest
    ): MutableLiveData<LoginResponse> {
        if (liveDataLogin == null) {
            liveDataLogin = MutableLiveData()
        }
        loginByPasswordAPI(request)
        return liveDataLogin as MutableLiveData<LoginResponse>
    }
    fun clearLocationData(): MutableLiveData<LoginResponse> {
        return liveDataLogin!!
    }
    fun clearLoginData(): MutableLiveData<LoginResponse> {
        return liveDataLogin!!
    }


    fun clearOTPData(): MutableLiveData<OTPResponse> {
        return liveDataOTP!!
    }



    internal fun getMyOTP(
        request: OTPRequest
    ): MutableLiveData<OTPResponse> {
        if (liveDataOTP == null) {
            liveDataOTP = MutableLiveData()
        }
        loadOTP(request)
        return liveDataOTP as MutableLiveData<OTPResponse>
    }


    internal fun loginByOTP(
        request: LoginRequestOTP
    ): MutableLiveData<LoginResponse> {
        if (liveDataLogin == null) {
            liveDataLogin = MutableLiveData()
        }
        loginByOTPAPI(request)
        return liveDataLogin as MutableLiveData<LoginResponse>
    }

    private fun dashBordApi() {

        val staffID =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.TOKEN
            )

        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.dashBoard(
           staffID

        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<DashboardResponce?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDashBoard?.postValue(DashboardResponce())
                }

                override fun onNext(response: DashboardResponce?) {
                    if (response != null) {
                        liveDashBoard?.postValue(response!!)
                    }
                }
            })
    }
    private fun profileApi() {

        val staffID =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.TOKEN
            )

        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.profile(
          staffID

        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<ProfileResponce?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveProfile?.postValue(ProfileResponce())
                }

                override fun onNext(response: ProfileResponce?) {
                    if (response != null) {
                        liveProfile?.postValue(response!!)
                    }
                }
            })
    }


    private fun loginByLocation(request: LoginRequest) {
        val staffID =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.TOKEN
            )
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.checkUserLocation(
            staffID,
           request.lat,
           request.lng)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<LocationResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {
                    liveDraction?.postValue(LocationResponse())
                }
                override fun onNext(response: LocationResponse?) {
                    if (response != null) {
                        liveDraction?.postValue(response!!)
                    }
                }
            })
    }

    private fun loadOTP(request: OTPRequest) {
        val staffID =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.TOKEN
            )
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.getOTP(
            staffID,
            ApiConstant.OBJ_HEADER_Content_Type,
            request.dial_code,
            request.phone_number,
            request.country_code,
            request.verification_code,
            request.lat,
            request.lng,
            request.stationid
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<OTPResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {
                    liveDataOTP?.postValue(OTPResponse())
                }

                override fun onNext(response: OTPResponse?) {
                    if (response != null) {
                        liveDataOTP?.postValue(response!!)
                    }
                }
            })
    }

    //swap otp verification hit

    private fun otpverification(request: LoginSwapRequestOTP) {
        val staffID =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.TOKEN
            )

        val apiService: ApiServices = RetrofitBuilder.apiService


        apiService.swapOTPverfication(
//            ApiConstant.OBJ_HEADER_Content_Type,
            staffID,
          request.cuntery_code,
          request.mobilenumber,
           request.dail_dode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<SwapLocationResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {
                    liveOTPverfication?.postValue(SwapLocationResponse())
                }
                override fun onNext(response: SwapLocationResponse?) {
                    if (response != null) {
                        liveOTPverfication?.postValue(response!!)
                    }
                }
            })
    }




    private fun loginByPasswordAPI(request: LoginRequest) {
        val stringValue =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.TOKEN
            )
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.checkUserLogin(
            createPartFromString(request.lat),
            createPartFromString(request.lng))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<LoginResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataLogin?.postValue(LoginResponse())
                }

                override fun onNext(response: LoginResponse?) {
                    if (response != null) {
                        liveDataLogin?.postValue(response!!)
                    }
                }
            })
    }


    private fun loginByOTPAPI(request: LoginRequestOTP) {
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.verifyUser(
            createPartFromString(request.dial_code),
            createPartFromString(request.phone_number),
            createPartFromString(request.otp))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<LoginResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataLogin?.postValue(LoginResponse())
                }

                override fun onNext(response: LoginResponse?) {
                    if (response != null) {
                        liveDataLogin?.postValue(response!!)
                    }
                }
            })
    }




    private fun createPartFromString(stringData: String): RequestBody {
        return stringData.toRequestBody("text/plain".toMediaTypeOrNull())
    }

}

//val requestBody = HashMap<String, Any>().apply {
//    put("dial_code", request.dial_code)
//    put("mobile_number", request.phone_number)
//    put("country_code", request.country_code)
//    put("verification_code", request.verification_code)
//    put("lat", request.lat)
//    put("lng", request.lng)
//    put("station_id", request.stationid)
//}


