package com.mauto.myapplication.stactionhistory

import com.google.gson.annotations.SerializedName

data class SwapingStactionArrayResponse (

	@SerializedName("label"                   ) var label                 : String?           = null,
	@SerializedName("value"                   ) var value                 : String?           = null,
	@SerializedName("batteries" ) var batteries : ArrayList<StactionBatteryArrayResponse> = arrayListOf(),


)