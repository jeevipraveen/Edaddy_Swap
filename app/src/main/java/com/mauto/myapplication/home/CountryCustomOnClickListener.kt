package com.mauto.myapplication.home


import android.view.View

interface CountryCustomOnClickListener {
    fun onItemClickListener(view: View, position: Int,code:String,codeshortname:String,codefullname:String)
}