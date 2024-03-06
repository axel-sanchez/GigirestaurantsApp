package com.example.gigirestaurantsapp.domain.usecase

import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import javax.inject.Inject
import javax.inject.Singleton

interface DislikeRestaurantUseCase{
    suspend fun call(restaurant: Restaurant)
}

@Singleton
class DislikeRestaurantUseCaseImpl @Inject constructor(private val repository: RestaurantRepository): DislikeRestaurantUseCase {
    override suspend fun call(restaurant: Restaurant) {
        repository.dislikeRestaurant(restaurant)
    }
}