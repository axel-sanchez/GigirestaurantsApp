package com.example.gigirestaurantsapp.domain.usecase

import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import javax.inject.Inject
import javax.inject.Singleton

interface LikeRestaurantUseCase{
    suspend fun call(restaurant: Restaurant)
}

@Singleton
class LikeRestaurantUseCaseImpl @Inject constructor(private val repository: RestaurantRepository): LikeRestaurantUseCase {
    override suspend fun call(restaurant: Restaurant) {
        repository.likeRestaurant(restaurant)
    }
}