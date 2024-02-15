package com.mauto.myapplication.login.model

import com.google.gson.annotations.SerializedName


data class FiltterRequest (
    @SerializedName("satrtdate")
    val satrtdate : String,
    @SerializedName("fromdate")
    val fromdate : String,
    @SerializedName("Vehicle_number")
    val vehicle_number : String,
    @SerializedName("Battery_number")
    val Battery_number : String,
    @SerializedName("Location")
    val Location : String,
    @SerializedName("show_all")
    val show_all : String,
)
