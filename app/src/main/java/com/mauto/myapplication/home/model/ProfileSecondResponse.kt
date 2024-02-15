package com.mauto.myapplication.home.model

import com.google.gson.annotations.SerializedName

data class ProfileSecondResponse (

	@SerializedName("message"                   ) var message                 : String?           = null,
	@SerializedName("profile") var profile : ProfileDetailsResponse= ProfileDetailsResponse()

)