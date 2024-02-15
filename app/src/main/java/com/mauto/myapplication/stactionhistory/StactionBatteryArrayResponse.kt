package com.mauto.myapplication.stactionhistory

import com.google.gson.annotations.SerializedName

data class StactionBatteryArrayResponse (

	@SerializedName("battery_no"                   ) var battery_no                 : String?           = null,
	@SerializedName("soc"                   ) var soc                 : String?           = null,


)