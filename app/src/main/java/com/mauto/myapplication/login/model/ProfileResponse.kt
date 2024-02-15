package com.mauto.myapplication.login.model

import com.google.gson.annotations.SerializedName


data class ProfileResponse (

  @SerializedName("status"   ) var status   : String?   = null,
  @SerializedName("response" ) var response : POResponse? = POResponse()

)