package com.mauto.myapplication.home.dashModel

import com.google.gson.annotations.SerializedName

data class DataDashCustomerResponse (

	@SerializedName("date"                   ) var date                 : String?           = null,
	@SerializedName("in_time"                   ) var in_time                 : String?           = null,
	@SerializedName("station"                   ) var station                 : String?           = null,
	@SerializedName("avail_battery" ) var avail_battery : ArrayList<DashAvailableResponse> = arrayListOf(),
	@SerializedName("monthly_stats") var monthlyStats: ArrayList<ArrayList<Int>> = arrayListOf(),
	@SerializedName("today_stats") var today_stats : DashTodayStatus = DashTodayStatus()


)