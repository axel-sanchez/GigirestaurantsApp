package com.example.gigirestaurantsapp.domain.usecase

import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import javax.inject.Inject
import javax.inject.Singleton

interface SaveRestaurantUseCase{
    suspend fun call(restaurant: Restaurant)
}

@Singleton
class SaveRestaurantUseCaseImpl @Inject constructor(private val repository: RestaurantRepository): SaveRestaurantUseCase {
    override suspend fun call(restaurant: Restaurant) {
        repository.saveRestaurant(restaurant)
    }
}