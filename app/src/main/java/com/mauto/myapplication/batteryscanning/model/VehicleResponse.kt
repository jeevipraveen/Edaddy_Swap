package com.mauto.myapplication.batteryscanning.model

import com.google.gson.annotations.SerializedName

data class VehicleResponse (
	@SerializedName("status") var status : String?   = null,

)