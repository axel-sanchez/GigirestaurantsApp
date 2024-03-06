package com.example.gigirestaurantsapp.data.repository

import androidx.lifecycle.LiveData
import com.example.gigirestaurantsapp.data.models.ApiError
import com.example.gigirestaurantsapp.data.models.ResponseRestoDetails
import com.example.gigirestaurantsapp.data.models.RestaurantDTO
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

    override suspend fun getNearbyRestaurants(location: String): RestaurantDTO {

        val nearbyRestaurants = restaurantRemoteSource.getNearbyRestaurants(location).value ?: RestaurantDTO(error = ApiError(
            GENERIC_ERROR.text, GENERIC_ERROR.text, GENERIC_CODE))

        val likedRestaurantsLiveData = restaurantLocalSource.getFavRestaurantsList()

        likedRestaurantsLiveData.forEach { restaurant ->
            nearbyRestaurants.restaurants?.find {
                if (restaurant.locationId == it?.locationId){
                    it.isLiked = true
                    true
                } else false
            }
        }

        return nearbyRestaurants
    }

    override suspend fun getRestaurantDetails(locationId: Int): ResponseRestoDetails? {
        return restaurantRemoteSource.getRestaurantDetails(locationId).value
    }

    override fun getFavRestaurantsLiveData(): LiveData<List<Restaurant>> {
        return restaurantLocalSource.getFavRestaurantsLiveData()
    }

    override suspend fun saveRestaurant(restaurant: Restaurant) {
        restaurantLocalSource.insertRestaurant(restaurant)
    }

    override suspend fun deleteRestaurant(restaurant: Restaurant) {
        restaurantLocalSource.deleteRestaurant(restaurant)
    }
}