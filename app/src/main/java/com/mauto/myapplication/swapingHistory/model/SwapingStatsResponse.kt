package com.mauto.myapplication.swapingHistory.model

import com.google.gson.annotations.SerializedName

data class SwapingStatsResponse (

	@SerializedName("no_of_swaps"                   ) var no_of_swaps                 : String?           = null,
	@SerializedName("total_energy"                   ) var total_energy                 : String?           = null,
	@SerializedName("time_taken"                   ) var time_taken                 : String?           = null,




)