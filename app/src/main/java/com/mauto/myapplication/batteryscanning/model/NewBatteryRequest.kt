package com.mauto.myapplication.batteryscanning.model

import com.google.gson.annotations.SerializedName


data class NewBatteryRequest (
    @SerializedName("barcode")
    val barcode : String,
    @SerializedName("vehicle_id")
    val vehicle_id : String,
    @SerializedName("oldbattery_id")
    val oldbattery_id : String
)
