package com.example.gigirestaurantsapp.domain.usecase

import com.example.gigirestaurantsapp.data.models.RestaurantDTO
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetRestaurantsBySearchUseCase{
    suspend fun call(query: String): RestaurantDTO
}

@Singleton
class GetRestaurantsBySearchUseCaseImpl @Inject constructor(private val repository: RestaurantRepository): GetRestaurantsBySearchUseCase {
    override suspend fun call(query: String): RestaurantDTO {
        return repository.getRestaurantsBySearch(query)
    }
}