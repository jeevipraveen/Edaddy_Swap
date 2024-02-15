package com.mauto.myapplication.login.model

import com.google.gson.annotations.SerializedName


data class LoginSwapRequestOTP (
    @SerializedName("cuntery_code")
    val cuntery_code : String,
    @SerializedName("mobilenumber")
    val mobilenumber : String,
    @SerializedName("dail_dode")
    val dail_dode : String

)
//cuntery_code, mobilenumber, dail_dode