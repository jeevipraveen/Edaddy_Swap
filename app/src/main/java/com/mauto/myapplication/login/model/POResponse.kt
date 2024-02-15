package com.mauto.myapplication.login.model

import com.google.gson.annotations.SerializedName


data class POResponse (

  @SerializedName("profile_info" ) var profileInfo : ProfileInfo? = ProfileInfo()

)