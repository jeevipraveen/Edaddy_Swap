package com.mauto.myapplication.swapingHistory.model

import com.google.gson.annotations.SerializedName

data class SwapingArrayResponse (

	@SerializedName("id"                   			) var id                		 : String?           = null,
	@SerializedName("timestamp"                  	 ) var timestamp                 : String?           = null,
	@SerializedName("vehicle_number"                   	) var vehicle_number             : String?           = null,
	@SerializedName("station"                  		 ) var station                 		 : String?           = null,
	@SerializedName("status"                   		) var status                 	 : String?           = null,
	@SerializedName("amount"                   		) var amount                 	 : String?           = null,
	@SerializedName("cycle_distance"                   ) var cycle_distance              : String?           = null,


)