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
    suspend fun getRestaurantsBySearch(query: String): List<Restaurant>
}

@Singleton
class RestaurantLocalSourceImpl @Inject constructor(private val database: RestaurantDao, private val locationHelper: LocationHelper):
    RestaurantLocalSource {

    override suspend fun insertRestaurant(restaurant: Restaurant) {
        database.insertRestaurant(restaurant)
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
        val latitude = locationHelper.getLatitudeFromLocationString(location)
        val longitude = locationHelper.getLongitudeFromLocationString(location)
        return database.getNearbyRestaurants(latitude, longitude)
    }

    override suspend fun getRestaurantsBySearch(query: String): List<Restaurant> {
        return database.getRestaurantsBySearch(query)
    }
}