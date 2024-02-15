package com.mauto.myapplication.swapingHistory.model

import com.google.gson.annotations.SerializedName

data class SwapingFilltterResponse (

	@SerializedName("stats") var stats : SwapingStatsResponse= SwapingStatsResponse(),
	@SerializedName("swap_history" ) var swap_history : ArrayList<SwapingFiltteringArrayResponse> = arrayListOf(),
//	@SerializedName("avail_battery" ) var avail_battery : ArrayList<SwapingArrayResponse> = arrayListOf(),

)