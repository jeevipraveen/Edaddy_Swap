package com.mauto.myapplication.login.model

import com.google.gson.annotations.SerializedName


data class OTResponse (

  @SerializedName("message"      ) var message     : String? = null,
  @SerializedName("token"      ) var token     : String? = null,
//
  @SerializedName("otp"          ) var otp         : String? = null,
//  @SerializedName("otp_status"   ) var otpStatus   : String? = null,
//  @SerializedName("do_action"    ) var doAction    : String? = null,
//  @SerializedName("resend_timer" ) var resendTimer : Int?    = null

)