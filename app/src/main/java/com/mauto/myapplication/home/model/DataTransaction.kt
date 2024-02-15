package com.mauto.myapplication.home.model

import com.google.gson.annotations.SerializedName

data class DataTransaction(
    @SerializedName("currency_code" ) var currencyCode : String? = null,
    @SerializedName("txn_id"        ) var txnId        : String? = null,
    @SerializedName("txn_amount"    ) var txnAmount    : Int?    = null,
    @SerializedName("gateway"       ) var gateway      : String? = null,
    @SerializedName("txn_date"      ) var txnDate      : String? = null
)
