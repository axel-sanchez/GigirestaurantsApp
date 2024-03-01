package com.example.gigirestaurantsapp.domain.repository

import com.example.gigirestaurantsapp.data.models.ResponseBody

/**
 * @author Axel Sanchez
 */
interface RestaurantRepository {
    suspend fun getNearbyRestaurants(location: String): ResponseBody
}