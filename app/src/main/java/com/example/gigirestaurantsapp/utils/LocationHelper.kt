package com.example.gigirestaurantsapp.utils

import android.content.Context
import android.location.LocationManager

class LocationHelper {

    fun getLatitudeFromLocationString(location: String): String{
        val coma = location.indexOf(',')
        if (coma == -1) return ""
        return location.substring(0, coma)
    }

    fun getLongitudeFromLocationString(location: String): String{
        val coma = location.indexOf(',')
        if (coma == -1) return ""
        return location.substring(coma+2, location.length)
    }

    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}