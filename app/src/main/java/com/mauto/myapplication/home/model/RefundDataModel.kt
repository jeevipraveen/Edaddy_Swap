package com.mauto.myapplication.home.model

import com.google.gson.annotations.SerializedName

data class RefundDataModel(

    @SerializedName("customerName") var customerName: String,
    @SerializedName("customerID") var customerID: String,
    @SerializedName("transactionID") var transactionID: String,
    @SerializedName("transactionAmount") var transactionAmount: String,
    @SerializedName("transactionDate") var transactionDate: String,
    @SerializedName("transactionStatus") var transactionStatus: String
)


