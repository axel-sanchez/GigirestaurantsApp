package com.example.gigirestaurantsapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.gigirestaurantsapp.data.models.ResponseRestoDetails
import com.example.gigirestaurantsapp.data.models.RestaurantDTO
import com.example.gigirestaurantsapp.data.models.Restaurant

/**
 * @author Axel Sanchez
 */
interface RestaurantRepository {
    suspend fun getNearbyRestaurants(location: String): RestaurantDTO
    suspend fun getRestaurantsBySearch(query: String): RestaurantDTO
    fun getFavRestaurantsLiveData(): LiveData<List<Restaurant>>
    suspend fun likeRestaurant(restaurant: Restaurant)
    suspend fun dislikeRestaurant(restaurant: Restaurant)
    suspend fun getRestaurantDetails(locationId: Int): ResponseRestoDetails?
}