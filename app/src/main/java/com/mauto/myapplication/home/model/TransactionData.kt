package com.mauto.myapplication.home.model

import com.google.gson.annotations.SerializedName

data class TransactionData(
    @SerializedName("customer_name" ) var customerName : String? = null,
    @SerializedName("customer_id"   ) var customerId   : String? = null,
    @SerializedName("phone_number"  ) var phoneNumber  : String? = null,
    @SerializedName("currency_code" ) var currencyCode : String? = null,
    @SerializedName("txn_amount"    ) var txnAmount    : String? = null,
    @SerializedName("gateway"       ) var gateway      : String? = null,
    @SerializedName("txn_date"      ) var txnDate      : String? = null,
    @SerializedName("txn_time"      ) var txnTime      : String? = null
)
