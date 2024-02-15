package com.mauto.myapplication.login.model

import com.google.gson.annotations.SerializedName


data class LocationRequest(
//    @SerializedName("dial_code")
//    val dial_code: String,
    @SerializedName("phone_number")
    val phone_number: String,
    @SerializedName("pword")
    val pword: String

)
