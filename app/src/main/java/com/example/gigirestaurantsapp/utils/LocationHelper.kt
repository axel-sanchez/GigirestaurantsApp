package com.example.gigirestaurantsapp.utils

class LocationHelper {

    fun getLatitudeFromLocationString(location: String): String{
        val coma = location.indexOf(',')
        return location.substring(0, coma)
    }

    fun getLongitudeFromLocationString(location: String): String{
        val coma = location.indexOf(',')
        return location.substring(coma+2, location.length)
    }
}