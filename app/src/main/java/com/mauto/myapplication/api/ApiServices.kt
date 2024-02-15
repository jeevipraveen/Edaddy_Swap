package com.mauto.myapplication.api

import com.mauto.myapplication.batteryscanning.model.NewBatteryResponse
import com.mauto.myapplication.batteryscanning.model.OldBatteryResponse
import com.mauto.myapplication.batteryscanning.model.VehicleResponse
import com.mauto.myapplication.home.dashModel.DashboardResponce
import com.mauto.myapplication.home.model.*
import com.mauto.myapplication.login.model.*
import com.mauto.myapplication.stactionhistory.StactionInforResponce
import com.mauto.myapplication.swapingHistory.model.SawapFiltterHistoryResponse
import com.mauto.myapplication.swapingHistory.model.SawapHistoryResponse
import okhttp3.RequestBody
import retrofit2.http.*
import retrofit2.http.GET
import retrofit2.http.POST
import rx.Observable
import retrofit2.http.Query


interface ApiServices {

    /*Login*/
//    @Multipart
//    @POST("v1/api/staff/login")
//    fun checkUserLogin(
////        @Part("dial_code") dial_code: RequestBody,
//        @Part("lat") lat: RequestBody,
//        @Part("lng") lng: RequestBody
//    ): Observable<LoginResponse>


    @GET("swapping/getstationslist")
    fun checkUserLocation(
        @Header("token") staffID: String,
        @Query("lat") lat: String,
        @Query("lng") lng: String
    ):Observable<LocationResponse>


    @GET("swapping/getverificationcode")
    fun swapOTPverfication(
        @Header("token") staffID: String,
        @Query("country_code") cuntery_code: String,
        @Query("mobile_number") mobilenumber: String,
        @Query("dial_code") dail_dode: String
    ):Observable<SwapLocationResponse>

    @GET("swapping/dashboard")
    fun dashBoard(
        @Header("token") staffID: String
    ): Observable<DashboardResponce>

    @GET("swapping/getprofile")
    fun profile(
        @Header("token") staffID: String
    ): Observable<ProfileResponce>


    @GET("swapping/history")
    fun swaphistory(
        @Header("token") stringValue: String
    ): Observable<SawapHistoryResponse>

    @GET("swapping/history")
    fun swapfilterhistory(
        @Header("token") stringValue: String,
        @Query("from") satrtdate: String,
        @Query("to") fromdate: String,
        @Query("vehicle_number") vehicle_number: String,
        @Query("battery_number") Battery_number: String,
        @Query("station_id") Location: String,
        @Query("show_all") show_all: String

    ): Observable<SawapFiltterHistoryResponse>

    @GET("swapping/getstationinfo")
    fun swapstionoverresult(
        @Header("token") stringValue: String,
        @Query("id") staction_id: String

    ): Observable<StactionInforResponce>


    @Multipart
    @POST("swapping/scanvehicle")
    fun vehicleQr(
        @Header("token") stringValue: String,
        @Part("scantxt") barcode: String
    ): Observable<VehicleResponse>
    @Multipart
    @POST("swapping/scanoldbattery")
    fun oldQr(
        @Header("token") stringValue: String,
        @Part("scantxt") barcode: String,
        @Part("vehicle_id") vehicle_id: String
    ): Observable<OldBatteryResponse>

    @Multipart
    @POST("swapping/scanoldbattery")
    fun newQr(
        @Header("token") stringValue: String,
        @Part("scantxt") barcode: String,
        @Part("vehicle_id") vehicle_id: String,
        @Part("scantxt") oldbattery_id: String
    ): Observable<NewBatteryResponse>

    @Multipart
    @POST("swapping/getstationslist")
    fun checkUserLogin(
        @Part("lat") lat: RequestBody,
        @Part("lng") lng: RequestBody
    ): Observable<LoginResponse>


    @FormUrlEncoded
    @Headers("Content-Type: text/plain")
    @POST("swapping/login")
    fun getOTP(
        @Header("token") staffID: String,
        @Header("Content-Type") Content_Type: String,
       @Field("dial_code") dial_code: String,
        @Field("mobile_number") mobile_number: String,
        @Field("country_code") country_code: String,
        @Field("verification_code") verification_code: String,
        @Field("lat") lat: String,
        @Field("lng") lng: String,
        @Field("station_id") station_id: String
    ): Observable<OTPResponse>



    /*Login*/
    @Multipart
    @POST("v1/api/staff/login")
    fun verifyUser(
        @Part("dial_code") dial_code: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part("otp") otp: RequestBody
    ): Observable<LoginResponse>


    @POST(" v1/api/staff/get/profile")
    fun getProfile(@Body otpRequest: OTPRequest): Observable<ProfileResponse>

    @Multipart
    @POST("v1/api/prebooking/details")
    fun getCustomerDetails(
        @Header("Drivertype") drivertype: String,
        @Part("id") id: RequestBody,
        @Part("dial_code") dial_code: RequestBody,
        @Part("mobile_number") mobile_number: RequestBody
    ): Observable<CustomerResponse>


    @Multipart
    @POST("v1/api/payment/list")
    fun getPaymentType(
        @Part("id") id: RequestBody,
        @Part("type") type: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("lon") lon: RequestBody,
        @Part("mode") mode: RequestBody
    ): Observable<PaymentTypeResponse>


    @Multipart
    @POST("v1/api/prebooking/model/update")
    fun updateVehicle(
        @Part("id") id: RequestBody,
        @Part("model") model: RequestBody,
        @Part("type") type: RequestBody,
        @Part("staff_id") staff_id: RequestBody
    ): Observable<VehicleTypeResponse>


    @Multipart
    @POST("v1/api/prebooking/payment/init")
    fun initPayment(
        @Part("id") id: RequestBody,
        @Part("type") type: RequestBody,
        @Part("pay_method") pay_method: RequestBody,
        @Part("dial_code") dial_code: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part("amount") amount: RequestBody,
        @Part("staff_id") staff_id: RequestBody
    ): Observable<PaymentResponse>


    @Multipart
    @POST("v1/api/prebooking/payment/cancel")
    fun cancelPayment(
        @Part("id") id: RequestBody,
        @Part("message") message: RequestBody,
        @Part("staff_id") staff_id: RequestBody
    ): Observable<CancelPaymentResponse>


    @Multipart
    @POST("v1/api/prebooking/payment/check")
    fun checkPayStatus(
        @Part("uuid") id: RequestBody
    ): Observable<PayementFinalResponse>

    @Multipart
    @POST("v1/api/staff/update/profile")
    fun profileUpdate(
        @Part("staff_id") staff_id: RequestBody,
        @Part("username") username: RequestBody,
        @Part("image") image: RequestBody
    ): Observable<CancelPaymentResponse>


    @Multipart
    @POST("v1/api/prebooking/transactions")
    fun getTransactionStatus(
        @Part("staff_id") staff_id: RequestBody, @Part("type") type: RequestBody
    ): Observable<TransactionFinalResponse>


    @Multipart
    @POST("v1/api/staff/logout")
    fun logOut(@Part("staff_id") staff_id: RequestBody): Observable<CancelPaymentResponse>

}