package com.mauto.myapplication.login.model

import com.google.gson.annotations.SerializedName


data class LoginRequest(

    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String
//    val username: String,
)
