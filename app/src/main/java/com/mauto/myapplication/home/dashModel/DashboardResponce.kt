package com.mauto.myapplication.home.dashModel
import com.google.gson.annotations.SerializedName


data class DashboardResponce (

    @SerializedName("status"   ) var status   : String?   = null,
    @SerializedName("response") var response : DashCustomerResponse = DashCustomerResponse()


)
