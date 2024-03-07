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
import com.example.gigirestaurantsapp.utils.Constants.GENERIC_ERROR_CODE
import com.example.gigirestaurantsapp.utils.Constants.LOCATION_ERROR_CODE
import com.example.gigirestaurantsapp.utils.LocationHelper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
class RestaurantRepositoryImpl @Inject constructor(
    private val restaurantRemoteSource: RestaurantRemoteSource,
    private val restaurantLocalSource: RestaurantLocalSource,
    private val locationHelper: LocationHelper
) : RestaurantRepository {

    override suspend fun getNearbyRestaurants(location: String): RestaurantDTO {

        if (location.isEmpty()) return RestaurantDTO(null, ApiError(LOCATION_ERROR.text, LOCATION_ERROR.text, LOCATION_ERROR_CODE))

        val localNearbyRestaurants = restaurantLocalSource.getNearbyRestaurants(location)

        if (localNearbyRestaurants.isNotEmpty()) return RestaurantDTO(localNearbyRestaurants, null)

        val nearbyRestaurants =
            restaurantRemoteSource.getNearbyRestaurants(location).value ?: RestaurantDTO(
                error = ApiError(
                    GENERIC_ERROR.text, GENERIC_ERROR.text, GENERIC_ERROR_CODE
                )
            )

        nearbyRestaurants.restaurants?.forEach {
            it?.let { restaurant ->
                restaurant.latitude = locationHelper.getLatitudeFromLocationString(location).toDouble()
                restaurant.longitude = locationHelper.getLongitudeFromLocationString(location).toDouble()
                restaurantLocalSource.insertRestaurant(restaurant)
            }
        }

        return RestaurantDTO(restaurantLocalSource.getNearbyRestaurants(location), null)
    }

    override suspend fun getRestaurantsBySearch(query: String): RestaurantDTO {

        val localNearbyRestaurants = restaurantLocalSource.getRestaurantsBySearch(query)

        if (localNearbyRestaurants.isNotEmpty()) return RestaurantDTO(localNearbyRestaurants, null)

        val restaurantsBySearch =
            restaurantRemoteSource.getRestaurantsBySearch(query).value ?: RestaurantDTO(
                error = ApiError(
                    GENERIC_ERROR.text, GENERIC_ERROR.text, GENERIC_ERROR_CODE
                )
            )

        restaurantsBySearch.restaurants?.forEach {
            it?.let { restaurant ->
                restaurant.query = query
                restaurantLocalSource.insertRestaurant(restaurant)
            }
        }

        return RestaurantDTO(restaurantLocalSource.getRestaurantsBySearch(query), null)
    }

    override suspend fun getRestaurantDetails(locationId: Int): ResponseRestoDetails? {
        return restaurantRemoteSource.getRestaurantDetails(locationId).value
    }

    override fun getLikedRestaurantsLiveData(): LiveData<List<Restaurant>> {
        return restaurantLocalSource.getLikedRestaurantsLiveData()
    }

    override suspend fun likeRestaurant(restaurant: Restaurant) {
        restaurantLocalSource.likeRestaurant(restaurant)
    }

    override suspend fun dislikeRestaurant(restaurant: Restaurant) {
        restaurantLocalSource.dislikeRestaurant(restaurant)
    }
}