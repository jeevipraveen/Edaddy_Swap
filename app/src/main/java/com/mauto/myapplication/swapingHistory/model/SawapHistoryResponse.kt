package com.mauto.myapplication.swapingHistory.model

import com.google.gson.annotations.SerializedName


data class SawapHistoryResponse (
	@SerializedName("status") var status : String?   = null,
	@SerializedName("response") var response : SwapingResponse = SwapingResponse()

)