package com.mauto.myapplication.batteryscanning.model

import com.google.gson.annotations.SerializedName

data class NewBatteryResponse (
	@SerializedName("status") var status : String?   = null,

)