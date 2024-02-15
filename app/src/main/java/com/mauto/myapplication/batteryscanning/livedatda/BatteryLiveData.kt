package com.mauto.myapplication.batteryscanning.livedatda

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauto.myapplication.api.ApiServices
import com.mauto.myapplication.api.RetrofitBuilder
import com.mauto.myapplication.batteryscanning.model.*
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

class BatteryLiveData  : ViewModel(){
    private var liveDataVehicleScann: MutableLiveData<VehicleResponse>? = null
    private var liveDataOldScann: MutableLiveData<OldBatteryResponse>? = null
    private var liveDataNewScann: MutableLiveData<NewBatteryResponse>? = null



    internal fun loginlocation(
        request: VheicleRequest
    ): MutableLiveData<VehicleResponse> {
        if (liveDataVehicleScann == null) {
            liveDataVehicleScann = MutableLiveData()
        }
        loginByPasswordAPI(request)
        return liveDataVehicleScann as MutableLiveData<VehicleResponse>
    }

    internal fun oldbattery(
        request: OldBatteryRequest
    ): MutableLiveData<OldBatteryResponse> {
        if (liveDataOldScann == null) {
            liveDataOldScann = MutableLiveData()
        }
        oldBatteryScanAPI(request)
        return liveDataOldScann as MutableLiveData<OldBatteryResponse>
    }
    internal fun newbattery(
        request: NewBatteryRequest
    ): MutableLiveData<NewBatteryResponse> {
        if (liveDataNewScann == null) {
            liveDataNewScann = MutableLiveData()
        }
        newBatteryScanAPI(request)
        return liveDataNewScann as MutableLiveData<NewBatteryResponse>
    }


    private fun loginByPasswordAPI(request: VheicleRequest) {
        val stringValue =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.TOKEN
            )
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.vehicleQr(
            stringValue,
            request.barcode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<VehicleResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataVehicleScann?.postValue(VehicleResponse())
                }

                override fun onNext(response: VehicleResponse?) {
                    if (response != null) {
                        liveDataVehicleScann?.postValue(response!!)
                    }
                }
            })
    }


    private fun oldBatteryScanAPI(request: OldBatteryRequest) {
        val stringValue =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.TOKEN
            )
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.oldQr(
            stringValue,
            request.barcode,
            request.vehicle_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<OldBatteryResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataOldScann?.postValue(OldBatteryResponse())
                }

                override fun onNext(response: OldBatteryResponse?) {
                    if (response != null) {
                        liveDataOldScann?.postValue(response!!)
                    }
                }
            })
    }  private fun newBatteryScanAPI(request: NewBatteryRequest) {
        val stringValue =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.TOKEN
            )
        val apiService: ApiServices = RetrofitBuilder.apiService
        apiService.newQr(stringValue,
            request.barcode,
            request.vehicle_id,
            request.oldbattery_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable -> Observable.error(throwable) }
            .subscribe(object : Subscriber<NewBatteryResponse?>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {

                    liveDataNewScann?.postValue(NewBatteryResponse())
                }

                override fun onNext(response: NewBatteryResponse?) {
                    if (response != null) {
                        liveDataNewScann?.postValue(response!!)
                    }
                }
            })
    }
}