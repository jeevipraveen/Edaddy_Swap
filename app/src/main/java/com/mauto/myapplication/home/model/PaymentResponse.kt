package com.mauto.myapplication.home.model

import com.google.gson.annotations.SerializedName


data class PaymentResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("response") var response: PaymResponse? = PaymResponse()
)



