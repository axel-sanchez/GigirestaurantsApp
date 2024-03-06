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
import com.example.gigirestaurantsapp.utils.LocationHelper
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

        val localNearbyRestaurants = restaurantLocalSource.getNearbyRestaurants(location)

        if (localNearbyRestaurants.isNotEmpty()) return RestaurantDTO(localNearbyRestaurants, null)

        val nearbyRestaurants =
            restaurantRemoteSource.getNearbyRestaurants(location).value ?: RestaurantDTO(
                error = ApiError(
                    GENERIC_ERROR.text, GENERIC_ERROR.text, GENERIC_CODE
                )
            )

        val locationHelper = LocationHelper()

        nearbyRestaurants.restaurants?.forEach {
            it?.let { restaurant ->
                restaurant.latitude = locationHelper.getLatitudeFromLocationString(location).toDouble()
                restaurant.longitude = locationHelper.getLongitudeFromLocationString(location).toDouble()
                restaurantLocalSource.insertRestaurant(restaurant)
            }
        }

        return RestaurantDTO(restaurantLocalSource.getNearbyRestaurants(location), null)
    }

    override suspend fun getRestaurantDetails(locationId: Int): ResponseRestoDetails? {
        return restaurantRemoteSource.getRestaurantDetails(locationId).value
    }

    override fun getFavRestaurantsLiveData(): LiveData<List<Restaurant>> {
        return restaurantLocalSource.getFavRestaurantsLiveData()
    }

    override suspend fun likeRestaurant(restaurant: Restaurant) {
        restaurantLocalSource.likeRestaurant(restaurant)
    }

    override suspend fun dislikeRestaurant(restaurant: Restaurant) {
        restaurantLocalSource.dislikeRestaurant(restaurant)
    }
}