package com.mauto.myapplication.home.livedata


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauto.myapplication.api.ApiServices
import com.mauto.myapplication.api.RetrofitBuilder
import com.mauto.myapplication.api.error.NetworkState
import com.mauto.myapplication.home.model.*
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
import java.io.File


class HomeLiveData : ViewModel() {
    private var liveDataCustomer: MutableLiveData<CustomerResponse>? = null
    private var liveDataPayType: MutableLiveData<PaymentTypeResponse>? = null
    private var liveDataMakePayment: MutableLiveData<PaymentResponse>? = null
    private var liveDatacheckPayment: MutableLiveData<PayementFinalResponse>? = null
    private var liveDataCancelPayment: MutableLiveData<CancelPaymentResponse>? = null
    private var liveDataProfile: MutableLiveData<CancelPaymentResponse>? = null
    private var liveDataLogout: MutableLiveData<CancelPaymentResponse>? = null
    private var liveDataTransaction: MutableLiveData<TransactionFinalResponse>? = null


    private val mState = MutableLiveData<NetworkState>()

    fun clearCustomerData(): MutableLiveData<CustomerResponse> {
        return liveDataCustomer as  MutableLiveData<CustomerResponse>
    }

    fun clearDataPayType(): MutableLiveData<PaymentTypeResponse> {
        return liveDataPayType!!
    }

    fun clearDataMakePayment(): MutableLiveData<PaymentResponse> {
        return liveDataMakePayment!!
    }

    fun clearDataCancelPayment(): MutableLiveData<CancelPaymentResponse> {
        return liveDataCancelPayment!!
    }

    fun clearDataProfile(): MutableLiveData<CancelPaymentResponse> {
        return liveDataProfile!!
    }

    fun clearDataLogout(): MutableLiveData<CancelPaymentResponse> {
        return liveDataLogout!!
    }


    fun clearPayStatus(): MutableLiveData<PayementFinalResponse> {
        if (liveDatacheckPayment == null) {
            liveDatacheckPayment = MutableLiveData()
        }
        return liveDatacheckPayment!!
    }

    fun clearTransaction(): MutableLiveData<TransactionFinalResponse> {
        return liveDataTransaction!!
    }

    internal fun getHistory(
        type: String
    ): MutableLiveData<TransactionFinalResponse> {
        if (liveDataTransaction == null) {
            liveDataTransaction = MutableLiveData()
        }
        historyAPI(type)
        return liveDataTransaction as MutableLiveData<TransactionFinalResponse>
    }


    internal fun getCustomerDetails(
        code: String, mobileNo: String
    ): MutableLiveData<CustomerResponse> {
        if (liveDataCustomer == null) {
            liveDataCustomer = MutableLiveData()
        }

        customerDetailsAPI(code, mobileNo)
        return liveDataCustomer as MutableLiveData<CustomerResponse>
    }


    internal fun uploadProfileImage(
        userName: String, Picture: File
    ): MutableLiveData<CancelPaymentResponse> {
        if (liveDataProfile == null) {
            liveDataProfile = MutableLiveData()
        }

        uploadProfileImageAPI(userName, Picture)
        return liveDataProfile as MutableLiveData<CancelPaymentResponse>
    }


    internal fun logout(

    ): MutableLiveData<CancelPaymentResponse> {
        if (liveDataLogout == null) {
            liveDataLogout = MutableLiveData()
        }
        logOutAPI()
        return liveDataLogout as MutableLiveData<CancelPaymentResponse>
    }


    internal fun getPayType(
    ): MutableLiveData<PaymentTypeResponse> {
        if (liveDataPayType == null) {
            liveDataPayType = MutableLiveData()
        }
        paymentTypeAPI()
        return liveDataPayType as MutableLiveData<PaymentTypeResponse>
    }


    internal fun cancelPayment(
    ): MutableLiveData<CancelPaymentResponse> {
        if (liveDataCancelPayment == null) {
            liveDataCancelPayment = MutableLiveData()
        }
        cancelPaymentAPI()
        return liveDataCancelPayment as MutableLiveData<CancelPaymentResponse>
    }

    internal fun makePayment(
    ): MutableLiveData<PaymentResponse> {
        if (liveDataMakePayment == null) {
            liveDataMakePayment = MutableLiveData()
        }
        makePaymentAPI()
        return liveDataMakePayment as MutableLiveData<PaymentResponse>
    }


    internal fun checkPayment(
    ): MutableLiveData<PayementFinalResponse> {
        if (liveDatacheckPayment == null) {
            liveDatacheckPayment = MutableLiveData()
        }
        checkPaymentAPI()
        return liveDatacheckPayment as MutableLiveData<PayementFinalResponse>
    }


    private fun customerDetailsAPI(code: String, mobileNo: String) {
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.getCustomerDetails(
            ApiConstant.OBJ_HEADER_DRIVER_SALES,
            createPartFromString(""),
            createPartFromString(code),
            createPartFromString(mobileNo)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<CustomerResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataCustomer?.postValue(CustomerResponse())
                }

                override fun onNext(response: CustomerResponse?) {
                    if (response != null) {
                        liveDataCustomer?.postValue(response!!)

                    }
                }
            })
    }


    private fun paymentTypeAPI() {

        val stringValue =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.SECRET_TOKEN
            )
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.getPaymentType(
            createPartFromString(stringValue),
            createPartFromString(ApiConstant.OBJ_HEADER_DRIVER_SALES),
            createPartFromString(""),
            createPartFromString(""),
            createPartFromString(ApiConstant.OBJ_HEADER_PRE_BOOKING)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<PaymentTypeResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataPayType?.postValue(PaymentTypeResponse())
                }

                override fun onNext(response: PaymentTypeResponse?) {
                    if (response != null) {
                        liveDataPayType?.postValue(response!!)
                    }
                }
            })
    }


    private fun historyAPI(type: String) {

        val stringValue =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.SECRET_TOKEN
            )
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.getTransactionStatus(
            createPartFromString(stringValue),
            createPartFromString(type)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<TransactionFinalResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {
                    liveDataTransaction?.postValue(TransactionFinalResponse())
                }

                override fun onNext(response: TransactionFinalResponse?) {
                    if (response != null) {
                        liveDataTransaction?.postValue(response!!)
                    }
                }
            })
    }


    private fun makePaymentAPI() {

        val staffID =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.SECRET_TOKEN
            )
        val custID =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.MAT_CUSTOMER_ID
            )
        val code =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.MAT_CUSTOMER_PHONE_CODE
            )
        val number =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.MAT_CUSTOMER_PHONE_NO
            )

        val numberEntered =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.MAT_PAY_MOBILE
            )
        val amount = MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context)
            .getStringValue(PrefConstant.MAT_PAY_AMOUNT)

        val method = MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context)
            .getStringValue(PrefConstant.MAT_PAY_METHOD)

        var fMobileNo = "";
        fMobileNo = numberEntered.ifEmpty { number }

        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.initPayment(
            createPartFromString(custID),
            createPartFromString(ApiConstant.OBJ_HEADER_DRIVER_SALES),
            createPartFromString(method),
            createPartFromString(code), createPartFromString(fMobileNo),
            createPartFromString(amount), createPartFromString(staffID)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<PaymentResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataMakePayment?.postValue(PaymentResponse())
                }

                override fun onNext(response: PaymentResponse?) {
                    if (response != null) {
                        liveDataMakePayment?.postValue(response!!)
                    }
                }
            })
    }


    private fun checkPaymentAPI() {

        val uuID =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.MAT_STATUS_UUID
            )


        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.checkPayStatus(
            createPartFromString(uuID)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<PayementFinalResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDatacheckPayment?.postValue(PayementFinalResponse())
                }

                override fun onNext(response: PayementFinalResponse?) {
                    if (response != null) {
                        liveDatacheckPayment?.postValue(response!!)
                    }
                }
            })
    }


    private fun cancelPaymentAPI() {

        val staffID =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.SECRET_TOKEN
            )
        val customerID =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.MAT_CUSTOMER_ID
            )

        val reason =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.MAT_CANCEL_REASON
            )
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.cancelPayment(
            createPartFromString(customerID),
            createPartFromString(reason),
            createPartFromString(staffID)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<CancelPaymentResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataCancelPayment?.postValue(CancelPaymentResponse())
                }

                override fun onNext(response: CancelPaymentResponse?) {
                    if (response != null) {
                        liveDataCancelPayment?.postValue(response!!)
                    }
                }
            })
    }


    private fun uploadProfileImageAPI(userName: String, Picture: File) {

        val staffID =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.SECRET_TOKEN
            )

        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.profileUpdate(
            createPartFromString(staffID),
            createPartFromString(userName),
            createPartFromString(Picture.name)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<CancelPaymentResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataProfile?.postValue(CancelPaymentResponse())
                }

                override fun onNext(response: CancelPaymentResponse?) {
                    if (response != null) {
                        liveDataProfile?.postValue(response!!)
                    }
                }
            })
    }


    private fun logOutAPI() {

        val staffID =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.SECRET_TOKEN
            )

        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.logOut(
            createPartFromString(staffID)

        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<CancelPaymentResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataLogout?.postValue(CancelPaymentResponse())
                }

                override fun onNext(response: CancelPaymentResponse?) {
                    if (response != null) {
                        liveDataLogout?.postValue(response!!)
                    }
                }
            })
    }


    private fun createPartFromString(stringData: String): RequestBody {
        return stringData.toRequestBody("text/plain".toMediaTypeOrNull())
    }

}