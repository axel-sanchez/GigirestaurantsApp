package com.example.gigirestaurantsapp.data.repository

import androidx.lifecycle.LiveData
import com.example.gigirestaurantsapp.data.models.ResponseRestoDetails
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.data.models.RestaurantDTO
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository

class FakeRepository : RestaurantRepository {
    override suspend fun getNearbyRestaurants(location: String): RestaurantDTO {
        TODO("Not yet implemented")
    }

    override suspend fun getRestaurantsBySearch(query: String): RestaurantDTO {
        TODO("Not yet implemented")
    }

    override fun getLikedRestaurantsLiveData(): LiveData<List<Restaurant>> {
        TODO("Not yet implemented")
    }

    override suspend fun likeRestaurant(restaurant: Restaurant) {
        TODO("Not yet implemented")
    }

    override suspend fun dislikeRestaurant(restaurant: Restaurant) {
        TODO("Not yet implemented")
    }

    override suspend fun getRestaurantDetails(locationId: Int): ResponseRestoDetails? {
        TODO("Not yet implemented")
    }

    val restaurant1 = Restaurant(
        locationId = 1,
        name = "Italy",
        latitude = -31.418119675147636,
        longitude = -64.49176343201465
    )
    val restaurant2 = Restaurant(
        locationId = 2,
        name = "Angus",
        latitude = -31.418119675147636,
        longitude = -64.49176343201465
    )
    val restaurant3 = Restaurant(
        locationId = 3,
        name = "Lomito 2x1",
        latitude = -31.418119675147636,
        longitude = -64.49176343201465
    )
    val restaurant4 = Restaurant(
        locationId = 4,
        name = "Pizza Ranch",
        latitude = -31.418119675147636,
        longitude = -64.49176343201465
    )

    fun getRemoteProducts(): RestaurantDTO {
        val restaurantList = listOf(
            restaurant1,
            restaurant2,
            restaurant3,
            restaurant4
        )
        return RestaurantDTO(
            restaurantList,
            null
        )
    }

    companion object {
        const val LOCATION = "-31.418119675147636, -64.49176343201465"
        const val QUERY = "Lomito"
    }
}