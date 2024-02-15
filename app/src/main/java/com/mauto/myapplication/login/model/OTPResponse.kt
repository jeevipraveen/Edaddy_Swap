package com.mauto.myapplication.login.model

import com.google.gson.annotations.SerializedName


data class OTPResponse (

  @SerializedName("status"   ) var status   : String?   = null,
//  @SerializedName("response"   ) var response   : String?   = null,
  @SerializedName("response" ) var response : OTResponse? = OTResponse()

)