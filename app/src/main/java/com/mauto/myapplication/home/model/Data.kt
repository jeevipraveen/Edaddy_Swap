package com.mauto.myapplication.home.model

import com.google.gson.annotations.SerializedName


data class Data (

    @SerializedName("uuid"           ) var uuid          : String? = null,
    @SerializedName("transaction_id" ) var transactionId : String? = null,
   // @SerializedName("pay_load"       ) var payLoad       : String? = null

)