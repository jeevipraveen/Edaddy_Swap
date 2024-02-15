package com.mauto.myapplication.swapingHistory.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauto.myapplication.api.ApiServices
import com.mauto.myapplication.api.RetrofitBuilder
import com.mauto.myapplication.batteryscanning.model.VehicleResponse
import com.mauto.myapplication.batteryscanning.model.VheicleRequest
import com.mauto.myapplication.home.model.CancelPaymentResponse
import com.mauto.myapplication.home.model.PaymentResponse
import com.mauto.myapplication.login.model.FiltterRequest
import com.mauto.myapplication.login.model.LocationResponse
import com.mauto.myapplication.login.model.LoginRequest
import com.mauto.myapplication.login.model.OTPRequest
import com.mauto.myapplication.stactionhistory.StactionInforResponce
import com.mauto.myapplication.stactionhistory.StactionRequest
import com.mauto.myapplication.swapingHistory.model.HistoryRequest
import com.mauto.myapplication.swapingHistory.model.SawapFiltterHistoryResponse
import com.mauto.myapplication.swapingHistory.model.SawapHistoryResponse
import com.mauto.myapplication.utils.MAutoApplication
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.PrefConstant
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SawpPaymentHistory: ViewModel(){
    private var liveDataSwapHistory: MutableLiveData<SawapHistoryResponse>? = null
    private var liveDataSwapFilterHistory: MutableLiveData<SawapFiltterHistoryResponse>? = null
    private var liveSwapStactionInform: MutableLiveData<StactionInforResponce>? = null


    internal fun swapstaioninfo(
        request: StactionRequest
    ): MutableLiveData<StactionInforResponce> {
        if (liveSwapStactionInform == null) {
            liveSwapStactionInform = MutableLiveData()
        }
        loginByLocation(request)
        return liveSwapStactionInform as MutableLiveData<StactionInforResponce>
    }

    internal fun swapHistory(
        request: HistoryRequest
    ): MutableLiveData<SawapHistoryResponse> {
        if (liveDataSwapHistory == null) {
            liveDataSwapHistory = MutableLiveData()
        }
        liveSwapHistory(request)
        return liveDataSwapHistory as MutableLiveData<SawapHistoryResponse>
    }

    internal fun swapoverallFillterHistory(
        request: FiltterRequest
    ): MutableLiveData<SawapFiltterHistoryResponse> {
        if (liveDataSwapFilterHistory == null) {
            liveDataSwapFilterHistory = MutableLiveData()
        }
        swapoverallFilterHistoryres(request)
        return liveDataSwapFilterHistory as MutableLiveData<SawapFiltterHistoryResponse>
    }

    internal fun swapoverallHistory(
    ): MutableLiveData<SawapHistoryResponse> {
        if (liveDataSwapHistory == null) {
            liveDataSwapHistory = MutableLiveData()
        }
        swapoverallHistoryres()
        return liveDataSwapHistory as MutableLiveData<SawapHistoryResponse>
    }

    private fun swapoverallFilterHistoryres(request: FiltterRequest) {

        val stringValue =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.TOKEN
            )
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.swapfilterhistory(
            stringValue,
        request.satrtdate,
        request.fromdate,
        request.vehicle_number,
        request.Battery_number,
        request.Location,
        request.show_all)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<SawapFiltterHistoryResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataSwapFilterHistory?.postValue(SawapFiltterHistoryResponse())
                }

                override fun onNext(response: SawapFiltterHistoryResponse?) {
                    if (response != null) {
                        liveDataSwapFilterHistory?.postValue(response!!)
                    }
                }
            })
    }
    private fun swapoverallHistoryres() {

        val stringValue =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.TOKEN
            )
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.swaphistory(stringValue)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<SawapHistoryResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataSwapHistory?.postValue(SawapHistoryResponse())
                }

                override fun onNext(response: SawapHistoryResponse?) {
                    if (response != null) {
                        liveDataSwapHistory?.postValue(response!!)
                    }
                }
            })
    }

    private fun loginByLocation(request: StactionRequest) {
        val stringValue =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.TOKEN
            )
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.swapstionoverresult(
            stringValue,
            request.staction_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<StactionInforResponce?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {
                    liveSwapStactionInform?.postValue(StactionInforResponce())
                }
                override fun onNext(response: StactionInforResponce?) {
                    if (response != null) {
                        liveSwapStactionInform?.postValue(response!!)
                    }
                }
            })
    }


    private fun liveSwapHistory(request: HistoryRequest) {
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.swaphistory(
            request.barcode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<SawapHistoryResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataSwapHistory?.postValue(SawapHistoryResponse())
                }

                override fun onNext(response: SawapHistoryResponse?) {
                    if (response != null) {
                        liveDataSwapHistory?.postValue(response!!)
                    }
                }
            })
    }
}