package com.example.gigirestaurantsapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.gigirestaurantsapp.data.models.ResponseBody
import com.example.gigirestaurantsapp.data.models.Restaurant

/**
 * @author Axel Sanchez
 */
interface RestaurantRepository {
    suspend fun getNearbyRestaurants(location: String): ResponseBody
    fun getFavoriteRestaurants(): LiveData<List<Restaurant>>
    suspend fun saveRestaurant(restaurant: Restaurant)
    suspend fun deleteRestaurant(restaurant: Restaurant)
}