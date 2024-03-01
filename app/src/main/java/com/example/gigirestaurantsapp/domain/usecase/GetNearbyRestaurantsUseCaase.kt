package com.example.gigirestaurantsapp.domain.usecase

import com.example.gigirestaurantsapp.data.models.ResponseBody
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetNearbyRestaurantsUseCase{
    suspend fun call(location: String): ResponseBody
}

@Singleton
class GetNearbyRestaurantsUseCaseImpl @Inject constructor(private val repository: RestaurantRepository): GetNearbyRestaurantsUseCase {
    override suspend fun call(location: String): ResponseBody {
        return repository.getNearbyRestaurants(location)
    }
}