package com.mauto.myapplication.home



import android.app.Application
import android.content.Context
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.myapplication.R

import java.util.*


class CountryCodeViewModel(application: Application) : AndroidViewModel(application)
{
    lateinit var array: Array<String>
    private val responseLiveData = MutableLiveData<ArrayList<CountrySelectionModel>>()
    private val filterresponseLiveData = MutableLiveData<ArrayList<CountrySelectionModel>>()

    fun countryarrayobserver(): MutableLiveData<ArrayList<CountrySelectionModel>>
    {
        return responseLiveData
    }
    fun filtercountryarrayobserver(): MutableLiveData<ArrayList<CountrySelectionModel>>
    {
        return filterresponseLiveData
    }
    fun filter(text: String,fullcountryarray: ArrayList<CountrySelectionModel>)
    {
        val filteredCourseAry: ArrayList<CountrySelectionModel> = ArrayList()
        val courseAry : ArrayList<CountrySelectionModel> = fullcountryarray
        for (eachCourse in courseAry)
        {
            if (eachCourse.countryname!!.toLowerCase().contains(text.toLowerCase()) || eachCourse.countrycode!!.toLowerCase().contains(text.toLowerCase()))
            {
                filteredCourseAry.add(eachCourse)
            }
        }
        filterresponseLiveData.value=filteredCourseAry
    }
    fun countryImageArray(mContext: Context)
    {
        var countryarraylist = ArrayList<CountrySelectionModel>()
        array = mContext.resources.getStringArray(R.array.CountryCodes)
        for (j in 0 until array.size)
        {
            val codeNameArray = array[j].split(",")
            val stringBuilder = StringBuilder()
            stringBuilder.append(getCountryZipCode(codeNameArray[1].trim()))
            val countryName = stringBuilder.toString()
            val code = codeNameArray[0].trim()
            val countryShortName = codeNameArray[1].trim()
            val imageBuilder = StringBuilder()
            imageBuilder.append("flags/flag_").append(countryShortName.toLowerCase()).append(".png")
            val inputStream = mContext.assets.open(imageBuilder.toString())
            val bitmap = BitmapFactory.decodeStream(inputStream)
            countryarraylist.add(CountrySelectionModel(code, stringBuilder.toString(),bitmap,countryShortName))
        }
        responseLiveData.value=countryarraylist
    }
    private fun getCountryZipCode(ssid: String): String
    {
        val loc = Locale("", ssid)
        return loc.displayCountry.trim { it <= ' ' }
    }
}

