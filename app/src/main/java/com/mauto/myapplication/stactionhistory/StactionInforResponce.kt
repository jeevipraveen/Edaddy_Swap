package com.mauto.myapplication.stactionhistory

import com.google.gson.annotations.SerializedName


data class StactionInforResponce (

    @SerializedName("status"   ) var status   : String?   = null,
    @SerializedName("response") var response : StactionOverallResultResponce= StactionOverallResultResponce()


)
