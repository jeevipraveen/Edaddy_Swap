package com.mauto.myapplication.home.dashModel

import com.google.gson.annotations.SerializedName

data class DashCustomerResponse (

	@SerializedName("message"                   ) var message                 : String?           = null,
	@SerializedName("data"          )    var data : DataDashCustomerResponse = DataDashCustomerResponse()

)