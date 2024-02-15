package com.mauto.myapplication.login.model

import com.google.gson.annotations.SerializedName


data class LoginRequestOTP (
    @SerializedName("dial_code")
    val dial_code : String,
    @SerializedName("phone_number")
    val phone_number : String,
    @SerializedName("otp")
    val otp : String

)
