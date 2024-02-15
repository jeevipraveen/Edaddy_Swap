package com.mauto.myapplication.login.model

import com.google.gson.annotations.SerializedName
import com.mauto.myapplication.home.model.SwapLocationData


data class LocationResponse (

  @SerializedName("status"   ) var status   : String?   = null,
  @SerializedName("response" ) var response : ArrayList<SwapLocationData> = arrayListOf()

)