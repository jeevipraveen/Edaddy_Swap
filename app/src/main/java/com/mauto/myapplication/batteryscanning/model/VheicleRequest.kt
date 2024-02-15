package com.mauto.myapplication.batteryscanning.model

import com.google.gson.annotations.SerializedName


data class VheicleRequest (
    @SerializedName("barcode")
    val barcode : String
)
