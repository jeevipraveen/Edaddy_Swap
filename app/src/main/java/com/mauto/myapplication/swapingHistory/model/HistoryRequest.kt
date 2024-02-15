package com.mauto.myapplication.swapingHistory.model

import com.google.gson.annotations.SerializedName


data class HistoryRequest (
    @SerializedName("barcode")
    val barcode : String,
    @SerializedName("vehicle_id")
    val vehicle_id : String
)
