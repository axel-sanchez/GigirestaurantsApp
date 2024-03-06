package com.example.gigirestaurantsapp.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gigirestaurantsapp.data.models.ApiError
import com.example.gigirestaurantsapp.data.models.ResponseRestoDetails
import com.example.gigirestaurantsapp.data.models.RestaurantDTO
import com.example.gigirestaurantsapp.data.service.ApiServiceRestaurant
import com.example.gigirestaurantsapp.utils.Constants.API_KEY
import com.example.gigirestaurantsapp.utils.Constants.ApiError.GENERIC_ERROR
import com.example.gigirestaurantsapp.utils.Constants.ApiError.NETWORK_ERROR
import com.example.gigirestaurantsapp.utils.Constants.GENERIC_CODE
import com.example.gigirestaurantsapp.utils.Constants.NETWORK_ERROR_CODE
import com.example.gigirestaurantsapp.utils.NetworkHelper
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

interface RestaurantRemoteSource {
    suspend fun getNearbyRestaurants(location: String): MutableLiveData<RestaurantDTO>
    suspend fun getRestaurantDetails(locationId: Int): MutableLiveData<ResponseRestoDetails?>
}

@Singleton
class RestaurantRemoteSourceImpl @Inject constructor(private val service: ApiServiceRestaurant,
                                                     private val networkHelper: NetworkHelper): RestaurantRemoteSource{

    override suspend fun getNearbyRestaurants(location: String): MutableLiveData<RestaurantDTO>{
        val mutableLiveData = MutableLiveData<RestaurantDTO>()

        try {
            if (!networkHelper.isOnline()) {
                mutableLiveData.value = RestaurantDTO(error = ApiError(message = NETWORK_ERROR.text, type = NETWORK_ERROR.text, code = NETWORK_ERROR_CODE))
                return mutableLiveData
            }

            val response = service.getNearbyRestaurants(API_KEY, location)
            if (response.isSuccessful) {
                Log.i("Successful Response", response.body().toString())
            } else {
                Log.i("Error Response", response.errorBody().toString())
            }
            mutableLiveData.value = response.body()
        } catch (e: IOException) {
            Log.e(
                "RestaurantRemoteSource",
                e.message?:"Error al obtener los restaurantes"
            )
            e.printStackTrace()
            mutableLiveData.value = RestaurantDTO(error = ApiError(e.message?:GENERIC_ERROR.text, GENERIC_ERROR.text, GENERIC_CODE))
        }

        return mutableLiveData
    }

    override suspend fun getRestaurantDetails(locationId: Int): MutableLiveData<ResponseRestoDetails?> {
        val mutableLiveData = MutableLiveData<ResponseRestoDetails?>()

        try {
            if (!networkHelper.isOnline()) {
                mutableLiveData.value = null
                return mutableLiveData
            }

            val response = service.getRestaurantDetails(locationId, API_KEY)
            Log.i("Request", response.raw().networkResponse()?.request()?.url().toString())
            if (response.isSuccessful) {
                Log.i("Successful Response", response.body().toString())
            } else {
                Log.i("Error Response", response.errorBody().toString())
            }
            mutableLiveData.value = response.body()
        } catch (e: IOException) {
            Log.e(
                "RestaurantRemoteSource",
                e.message?:"Error al obtener los detalles del restaurante"
            )
            e.printStackTrace()
            mutableLiveData.value = null
        }

        return mutableLiveData
    }
}