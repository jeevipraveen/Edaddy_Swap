package com.mauto.myapplication.home.model

import com.google.gson.annotations.SerializedName

data class CustomerResponse (
	@SerializedName("status") var status : String?   = null,
	@SerializedName("response") var response : CustResponse= CustResponse()
)