package com.example.gigirestaurantsapp.data.service

import com.example.gigirestaurantsapp.data.models.ResponseBody
import com.example.gigirestaurantsapp.utils.Constants.GET_NEARBY_RESTAURANTS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceRestaurant {
    @GET(GET_NEARBY_RESTAURANTS)
    suspend fun getNearbyRestaurants(
        @Query("key") key: String,
        @Query("latLong") latLong: String
    ): Response<ResponseBody>
}