package com.example.gigirestaurantsapp.data.repository

import com.example.gigirestaurantsapp.data.models.ApiError
import com.example.gigirestaurantsapp.data.models.ResponseBody
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.data.source.RestaurantLocalSource
import com.example.gigirestaurantsapp.data.source.RestaurantRemoteSource
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import com.example.gigirestaurantsapp.utils.Constants.ApiError.*
import com.example.gigirestaurantsapp.utils.Constants.GENERIC_CODE
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
class RestaurantRepositoryImpl @Inject constructor(
    private val restaurantRemoteSource: RestaurantRemoteSource,
    private val restaurantLocalSource: RestaurantLocalSource
) : RestaurantRepository {

    override suspend fun getNearbyRestaurants(location: String): ResponseBody {
        return restaurantRemoteSource.getNearbyRestaurants(location).value ?: ResponseBody(error = ApiError(
            GENERIC_ERROR.text, GENERIC_ERROR.text, GENERIC_CODE))
    }

    override suspend fun getFavoriteRestaurants(): List<Restaurant> {
        return restaurantLocalSource.getFavoriteRestaurants()
    }

    override suspend fun saveRestaurant(restaurant: Restaurant) {
        restaurantLocalSource.insertRestaurant(restaurant)
    }

    override suspend fun deleteRestaurant(restaurant: Restaurant) {
        restaurantLocalSource.deleteRestaurant(restaurant)
    }
}