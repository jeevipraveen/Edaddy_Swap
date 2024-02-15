package com.mauto.myapplication.batteryscanning.model

import com.google.gson.annotations.SerializedName


data class OldBatteryRequest (
    @SerializedName("barcode")
    val barcode : String,
    @SerializedName("vehicle_id")
    val vehicle_id : String
)
