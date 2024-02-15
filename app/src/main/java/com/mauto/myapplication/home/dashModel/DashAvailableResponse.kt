package com.mauto.myapplication.home.dashModel

import com.google.gson.annotations.SerializedName

data class DashAvailableResponse (

	@SerializedName("label"                   ) var label                 : String?           = null,
	@SerializedName("value"                   ) var value                 : String?           = null,
//	@SerializedName("station"                   ) var station                 : String?           = null,


)