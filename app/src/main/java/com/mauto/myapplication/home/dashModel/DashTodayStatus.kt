package com.mauto.myapplication.home.dashModel

import com.google.gson.annotations.SerializedName

data class DashTodayStatus (

	@SerializedName("no_of_swaps"                   ) var no_of_swaps                 : String?           = null,
	@SerializedName("total_energy"                   ) var total_energy                 : String?           = null,
	@SerializedName("time_taken"                   ) var time_taken                 : String?           = null,


)