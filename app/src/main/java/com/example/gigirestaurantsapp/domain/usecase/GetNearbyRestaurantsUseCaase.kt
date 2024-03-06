package com.example.gigirestaurantsapp.domain.usecase

import com.example.gigirestaurantsapp.data.models.RestaurantDTO
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetNearbyRestaurantsUseCase{
    suspend fun call(location: String): RestaurantDTO
}

@Singleton
class GetNearbyRestaurantsUseCaseImpl @Inject constructor(private val repository: RestaurantRepository): GetNearbyRestaurantsUseCase {
    override suspend fun call(location: String): RestaurantDTO {
        return repository.getNearbyRestaurants(location)
    }
}