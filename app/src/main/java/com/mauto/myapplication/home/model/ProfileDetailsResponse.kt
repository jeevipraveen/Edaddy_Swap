package com.mauto.myapplication.home.model

import com.google.gson.annotations.SerializedName

data class ProfileDetailsResponse (

	@SerializedName("id"                   ) var id                 : String?           = null,
	@SerializedName("agent_name"                   ) var agent_name                 : String?           = null,
	@SerializedName("first_name"                   ) var first_name                 : String?           = null,
	@SerializedName("last_name"                   ) var last_name                 : String?           = null,
	@SerializedName("email"                   ) var email                 : String?           = null,
	@SerializedName("dial_code"                   ) var dial_code                 : String?           = null,
	@SerializedName("gender"                   ) var gender                 : String?           = null,
	@SerializedName("mobile_number"                   ) var mobile_number                 : String?           = null,
	@SerializedName("lang_code"                   ) var lang_code                 : String?           = null


//	@SerializedName("avail_battery" ) var avail_battery : ArrayList<DashAvailableResponse> = arrayListOf(),
//	@SerializedName("today_stats") var today_stats : DashTodayStatus= DashTodayStatus()


)