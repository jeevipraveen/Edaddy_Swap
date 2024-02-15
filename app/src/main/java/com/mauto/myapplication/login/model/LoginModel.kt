package com.mauto.myapplication.login.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "LoginModel")
data class LoginModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int? ,
    @SerializedName("apiName")
    val apiName : String,
    @SerializedName("clientId")
    val clientId : String

)
