package com.example.gigirestaurantsapp.domain.repository

import com.example.gigirestaurantsapp.data.models.ResponseBody
import com.example.gigirestaurantsapp.data.models.Restaurant

/**
 * @author Axel Sanchez
 */
interface RestaurantRepository {
    suspend fun getNearbyRestaurants(location: String): ResponseBody
    suspend fun getFavoriteRestaurants(): List<Restaurant>
    suspend fun saveRestaurant(restaurant: Restaurant)
    suspend fun deleteRestaurant(restaurant: Restaurant)
}