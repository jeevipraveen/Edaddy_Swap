package com.mauto.myapplication.home.model


import com.google.gson.annotations.SerializedName


data class PaymResponse(

    @SerializedName("data") var data: Data? = Data(),
    @SerializedName("message") var message: String? = null

)