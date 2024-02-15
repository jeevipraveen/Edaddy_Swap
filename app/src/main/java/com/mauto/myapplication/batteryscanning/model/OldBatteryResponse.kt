package com.mauto.myapplication.batteryscanning.model

import com.google.gson.annotations.SerializedName

data class OldBatteryResponse (
	@SerializedName("status") var status : String?   = null,

)