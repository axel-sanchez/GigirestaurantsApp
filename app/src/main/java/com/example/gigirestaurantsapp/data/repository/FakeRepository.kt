package com.example.gigirestaurantsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.gigirestaurantsapp.data.models.ResponseRestoDetails
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.data.models.RestaurantDTO
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository

class FakeRepository : RestaurantRepository {
    override suspend fun getNearbyRestaurants(location: String): RestaurantDTO {
        return getRemoteProducts()
    }

    override suspend fun getRestaurantsBySearch(query: String): RestaurantDTO {
        return RestaurantDTO(restaurants = listOf(restaurant4, restaurantPizza))
    }

    override fun getLikedRestaurantsLiveData(): LiveData<List<Restaurant>> {
        return liveData { listOf(restaurant1, restaurant2) }
    }

    override suspend fun likeRestaurant(restaurant: Restaurant) {
        restaurant.isLiked = true
    }

    override suspend fun dislikeRestaurant(restaurant: Restaurant) {
        restaurant.isLiked = false
    }

    override suspend fun getRestaurantDetails(locationId: Int): ResponseRestoDetails? {
        return getResponseRestoDetails()
    }

    val restaurant1 = Restaurant(
        locationId = 1,
        name = "It Italy",
        description = "Restaurante Italiano ubicado en la calle libertad",
        latitude = -31.418119675147636,
        longitude = -64.49176343201465,
        isLiked = true
    )
    val restaurant2 = Restaurant(
        locationId = 2,
        name = "Angus",
        latitude = -31.418119675147636,
        longitude = -64.49176343201465,
        isLiked = true
    )
    val restaurant3 = Restaurant(
        locationId = 3,
        name = "Lomito 2x1",
        latitude = -31.418119675147636,
        longitude = -64.49176343201465,
        isLiked = false
    )
    val restaurant4 = Restaurant(
        locationId = 4,
        name = "Pizza Ranch",
        description = "Es una pizzeria ubicada en la calle Libertad",
        latitude = -31.418119675147636,
        longitude = -64.49176343201465,
        isLiked = false
    )

    val restaurantPizza = Restaurant(
        locationId = 5,
        name = "NYPD Pizza",
        latitude = -31.418119675147636,
        longitude = -64.49176343201465,
        isLiked = false
    )

    fun getRemoteProducts(): RestaurantDTO {
        val restaurantList = mutableListOf(
            restaurant1,
            restaurant2,
            restaurant3,
            restaurant4,
            restaurantPizza
        )
        return RestaurantDTO(
            restaurantList,
            null
        )
    }

    private fun getResponseRestoDetails(): ResponseRestoDetails{
        return ResponseRestoDetails(name = restaurant1.name, description = restaurant1.description)
    }

    companion object {
        const val LOCATION = "-31.418119675147636, -64.49176343201465"
        const val LATITUDE = "-31.418119675147636"
        const val LONGITUDE = "-64.49176343201465"
        const val QUERY = "Lomito"
        const val QUERY_PIZZA = "Pizza"
    }
}