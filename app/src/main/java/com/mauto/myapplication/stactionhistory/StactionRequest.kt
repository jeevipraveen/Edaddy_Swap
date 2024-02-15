package com.mauto.myapplication.stactionhistory

import com.google.gson.annotations.SerializedName


data class StactionRequest(

    @SerializedName("staction_id")
    val staction_id: String,
//    @SerializedName("lng")
//    val lng: String
////    val username: String,
)
