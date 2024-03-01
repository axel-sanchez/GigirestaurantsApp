package com.example.gigirestaurantsapp.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gigirestaurantsapp.data.models.ApiError
import com.example.gigirestaurantsapp.data.models.ResponseBody
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
    suspend fun getNearbyRestaurants(): MutableLiveData<ResponseBody>
}

@Singleton
class RestaurantRemoteSourceImpl @Inject constructor(private val service: ApiServiceRestaurant,
                                                     private val networkHelper: NetworkHelper): RestaurantRemoteSource{

    override suspend fun getNearbyRestaurants(): MutableLiveData<ResponseBody>{
        val mutableLiveData = MutableLiveData<ResponseBody>()

        try {
            if (!networkHelper.isOnline()) {
                mutableLiveData.value = ResponseBody(error = ApiError(message = NETWORK_ERROR.text, type = NETWORK_ERROR.text, code = NETWORK_ERROR_CODE))
                return mutableLiveData
            }

            val response = service.getNearbyRestaurants(API_KEY, "-31.418119675147636, -64.49176343201465")
            if (response.isSuccessful) {
                Log.i("Successful Response", response.body().toString())
            } else {
                Log.i("Error Response", response.errorBody().toString())
            }
            mutableLiveData.value = response.body()
        } catch (e: IOException) {
            Log.e(
                "RestaurantRemoteSourceImpl",
                e.message?:"Error al obtener los restaurantes"
            )
            e.printStackTrace()
            mutableLiveData.value = ResponseBody(error = ApiError(GENERIC_ERROR.text, GENERIC_ERROR.text, GENERIC_CODE))
        }

        return mutableLiveData
    }
}