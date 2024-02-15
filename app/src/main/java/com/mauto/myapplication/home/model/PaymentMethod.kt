package com.mauto.myapplication.home.model

import com.google.gson.annotations.SerializedName


data class PaymentMethod (

  @SerializedName("type"       ) var type      : String? = null,
  @SerializedName("code"       ) var code      : String? = null,
  @SerializedName("npn"        ) var npn       : String? = null,
  @SerializedName("is_default" ) var isDefault : String? = null
)