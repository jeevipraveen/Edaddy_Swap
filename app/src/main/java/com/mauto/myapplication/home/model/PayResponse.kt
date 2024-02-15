package com.mauto.myapplication.home.model

import com.google.gson.annotations.SerializedName


data class PayResponse (

  @SerializedName("payment_method" ) var paymentMethod : ArrayList<PaymentMethod> = arrayListOf(),
  @SerializedName("card_payment"   ) var cardPayment   : String?                  = null,
  @SerializedName("currency_code"  ) var currencyCode  : String?                  = null

)