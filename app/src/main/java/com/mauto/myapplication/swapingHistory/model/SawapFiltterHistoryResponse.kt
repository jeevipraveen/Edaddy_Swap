package com.mauto.myapplication.swapingHistory.model

import com.google.gson.annotations.SerializedName


data class SawapFiltterHistoryResponse (
	@SerializedName("status") var status : String?   = null,
	@SerializedName("response") var response : SwapingFilltterResponse = SwapingFilltterResponse()

)