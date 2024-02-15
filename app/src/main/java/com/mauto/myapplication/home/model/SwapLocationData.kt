package com.mauto.myapplication.home.model

import com.google.gson.annotations.SerializedName

data class SwapLocationData(
    @SerializedName("id" )                               var id : String? = null,
    @SerializedName("code"   )                           var code   : String? = null,
    @SerializedName("name"  )                           var name  : String? = null,
    @SerializedName("contact_number" )                  var contact_number : String? = null,
    @SerializedName("country_code" )                    var country_code    : String? = null,
    @SerializedName("image"       )                     var image      : String? = null,
    @SerializedName("geolocation")                      var geolocation      : String? = null,
    @SerializedName("lat"      )                        var lat      : String? = null,
    @SerializedName("lng"      )                        var lng      : String? = null,
    @SerializedName("total_batteries")                  var total_batteries      : String? = null,
    @SerializedName("status")                           var status      : String? = null,
    @SerializedName("charging_batteries" )               var charging_batteries      : String? = null,
    @SerializedName("available_batteries" )               var available_batteries      : String? = null,
    @SerializedName("total_swaps_no" )                  var total_swaps_no      : String? = null,
    @SerializedName("total_swaps_amount" )               var total_swaps_amount      : String? = null,
    @SerializedName("total_energy_served" )             var total_energy_served      : String? = null,
    @SerializedName("total_swap_duration" )             var total_swap_duration      : String? = null
)
