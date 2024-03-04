package com.example.gigirestaurantsapp.domain.usecase

import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import javax.inject.Inject
import javax.inject.Singleton

interface GetFavoriteRestaurantsUseCase{
    suspend fun call(): List<Restaurant>
}

@Singleton
class GetFavoriteRestaurantsUseCaseImpl @Inject constructor(private val repository: RestaurantRepository): GetFavoriteRestaurantsUseCase {
    override suspend fun call(): List<Restaurant> {
        return repository.getFavoriteRestaurants()
    }
}