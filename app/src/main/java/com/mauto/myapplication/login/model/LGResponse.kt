package com.mauto.myapplication.login.model

import com.google.gson.annotations.SerializedName


data class LGResponse (

  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("profile" ) var profile : Profile? = Profile()

)