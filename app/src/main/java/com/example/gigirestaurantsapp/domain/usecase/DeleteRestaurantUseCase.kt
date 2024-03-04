package com.example.gigirestaurantsapp.domain.usecase

import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import javax.inject.Inject
import javax.inject.Singleton

interface DeleteRestaurantUseCase{
    suspend fun call(restaurant: Restaurant)
}

@Singleton
class DeleteRestaurantUseCaseImpl @Inject constructor(private val repository: RestaurantRepository): DeleteRestaurantUseCase {
    override suspend fun call(restaurant: Restaurant) {
        repository.deleteRestaurant(restaurant)
    }
}