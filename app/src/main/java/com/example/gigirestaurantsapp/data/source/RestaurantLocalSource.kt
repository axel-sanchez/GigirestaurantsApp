package com.example.gigirestaurantsapp.data.source

import androidx.lifecycle.LiveData
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
    fun getFavRestaurantsLiveData(): LiveData<List<Restaurant>>
    suspend fun getFavRestaurantsList(): List<Restaurant>
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

    override fun getFavRestaurantsLiveData(): LiveData<List<Restaurant>> {
        return database.getFavRestaurantsLiveData()
    }

    override suspend fun getFavRestaurantsList(): List<Restaurant> {
        return database.getFavRestaurantsList()
    }
}