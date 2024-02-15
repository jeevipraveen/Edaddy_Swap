package com.mauto.myapplication.stactionhistory

import com.google.gson.annotations.SerializedName


data class StactionOverallResultResponce (

    @SerializedName("id"   ) var id   : String?   = null,
    @SerializedName("code"   ) var code   : String?   = null,
    @SerializedName("name"   ) var name   : String?   = null,
    @SerializedName("contact_number"   ) var contact_number   : String?   = null,
    @SerializedName("country_code"   ) var country_code   : String?   = null,
    @SerializedName("image"   ) var image   : String?   = null,
    @SerializedName("geolocation"   ) var geolocation   : String?   = null,
    @SerializedName("lat"   ) var lat   : String?   = null,
    @SerializedName("lng"   ) var lng   : String?   = null,
    @SerializedName("avail_battery" ) var avail_battery : ArrayList<SwapingStactionArrayResponse> = arrayListOf(),
//    @SerializedName("response") var response : DashCustomerResponse= DashCustomerResponse()


)
