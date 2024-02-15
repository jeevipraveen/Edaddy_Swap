package com.mauto.myapplication.swapingHistory.model

import com.google.gson.annotations.SerializedName

data class SwapingResponse (

//	@SerializedName("message"                   ) var message                 : String?           = null,
	@SerializedName("stats") var stats : SwapingStatsResponse= SwapingStatsResponse(),
	@SerializedName("swap_history" ) var swap_history : ArrayList<SwapingArrayResponse> = arrayListOf(),
//	@SerializedName("avail_battery" ) var avail_battery : ArrayList<SwapingArrayResponse> = arrayListOf(),

)