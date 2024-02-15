package com.mauto.myapplication.home.model

import com.google.gson.annotations.SerializedName

data class TransactionResponse( @SerializedName("transactions" ) var data : ArrayList<TransactionData> = arrayListOf())
