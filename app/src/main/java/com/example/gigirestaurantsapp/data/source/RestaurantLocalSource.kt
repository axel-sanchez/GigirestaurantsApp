package com.example.gigirestaurantsapp.data.source

import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.data.room.RestaurantDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface RestaurantLocalSource {
    suspend fun insertRestaurant(restaurant: Restaurant)
    suspend fun deleteRestaurant(restaurant: Restaurant)
    suspend fun getFavoriteRestaurants(): List<Restaurant>
}

@Singleton
class RestaurantLocalSourceImpl @Inject constructor(private val database: RestaurantDao):
    RestaurantLocalSource {

    override suspend fun insertRestaurant(restaurant: Restaurant) {
        database.insertRestaurant(restaurant)
    }

    override suspend fun deleteRestaurant(restaurant: Restaurant) {
        database.deleteRestaurant(restaurant)
    }

    override suspend fun getFavoriteRestaurants(): List<Restaurant> {
        return database.getFavoriteRestaurants()
    }
}