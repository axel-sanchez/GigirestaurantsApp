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
    fun getFavRestaurantsLiveData(): LiveData<List<Restaurant>>
    suspend fun saveRestaurant(restaurant: Restaurant)
    suspend fun deleteRestaurant(restaurant: Restaurant)
    suspend fun getRestaurantDetails(locationId: Int): ResponseRestoDetails?
}