package com.example.gigirestaurantsapp.data.service

import com.example.gigirestaurantsapp.data.models.ResponseRestoDetails
import com.example.gigirestaurantsapp.data.models.RestaurantDTO
import com.example.gigirestaurantsapp.utils.Constants.GET_NEARBY_RESTAURANTS
import com.example.gigirestaurantsapp.utils.Constants.GET_RESTAURANT_DETAILS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceRestaurant {
    @GET(GET_NEARBY_RESTAURANTS)
    suspend fun getNearbyRestaurants(
        @Query("key") key: String,
        @Query("latLong") latLong: String
    ): Response<RestaurantDTO>

    @GET("{locationId}/$GET_RESTAURANT_DETAILS")
    suspend fun getRestaurantDetails(
        @Path("locationId") locationId: Int,
        @Query("key") key: String
    ): Response<ResponseRestoDetails>
}