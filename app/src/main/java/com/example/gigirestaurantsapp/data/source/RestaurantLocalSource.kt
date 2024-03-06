package com.example.gigirestaurantsapp.data.source

import androidx.lifecycle.LiveData
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.data.room.RestaurantDao
import com.example.gigirestaurantsapp.utils.LocationHelper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface RestaurantLocalSource {
    suspend fun insertRestaurant(restaurant: Restaurant)
    suspend fun dislikeRestaurant(restaurant: Restaurant)
    suspend fun likeRestaurant(restaurant: Restaurant)
    fun getFavRestaurantsLiveData(): LiveData<List<Restaurant>>
    suspend fun getNearbyRestaurants(location: String): List<Restaurant>
}

@Singleton
class RestaurantLocalSourceImpl @Inject constructor(private val database: RestaurantDao):
    RestaurantLocalSource {

    override suspend fun insertRestaurant(restaurant: Restaurant) {
        val i = database.insertRestaurant(restaurant)
    }

    override suspend fun dislikeRestaurant(restaurant: Restaurant) {
        database.updateRestaurant(restaurant)
    }

    override suspend fun likeRestaurant(restaurant: Restaurant) {
        database.updateRestaurant(restaurant)
    }

    override fun getFavRestaurantsLiveData(): LiveData<List<Restaurant>> {
        return database.getFavRestaurantsLiveData()
    }

    override suspend fun getNearbyRestaurants(location: String): List<Restaurant> {
        val locationHelper = LocationHelper()
        val latitude = locationHelper.getLatitudeFromLocationString(location)
        val longitude = locationHelper.getLongitudeFromLocationString(location)
        return database.getNearbyRestaurants(latitude, longitude)
    }
}