package com.mauto.myapplication.home.dashModel

import com.google.gson.annotations.SerializedName

data class MonthlyStatusResponse (

	@SerializedName("month") val month: Int,
	@SerializedName("value") val value: Int

)