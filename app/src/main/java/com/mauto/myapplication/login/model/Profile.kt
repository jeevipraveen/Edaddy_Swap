package com.mauto.myapplication.login.model

import com.google.gson.annotations.SerializedName


data class Profile (

  @SerializedName("staff_id"     ) var staffId     : String? = null,
  @SerializedName("name"         ) var name        : String? = null,
  @SerializedName("dial_code"    ) var dialCode    : String? = null,
  @SerializedName("phone_number" ) var phoneNumber : String? = null,
  @SerializedName("email"        ) var email       : String? = null,
  @SerializedName("image"        ) var image       : String? = null,
  @SerializedName("lang"        ) var lang       : String? = null,

)