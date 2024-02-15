package com.mauto.myapplication.login.model

import com.google.gson.annotations.SerializedName


data class OTPRequest (
    @SerializedName("dial_code")
    val dial_code : String,
    @SerializedName("phone_number")
    val phone_number : String,
    @SerializedName("country_code")
    val country_code : String,
    @SerializedName("verification_code")
    val verification_code : String,
    @SerializedName("stationid")
    val stationid : String,
    @SerializedName("lat")
    val lat : String,
    @SerializedName("lng")
    val lng : String


)
