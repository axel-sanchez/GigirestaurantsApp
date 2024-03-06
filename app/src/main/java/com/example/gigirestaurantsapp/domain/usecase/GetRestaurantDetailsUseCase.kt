package com.example.gigirestaurantsapp.domain.usecase

import com.example.gigirestaurantsapp.data.models.ResponseRestoDetails
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetRestaurantDetailsUseCase{
    suspend fun call(locationId: Int): ResponseRestoDetails?
}

@Singleton
class GetRestaurantDetailsUseCaseImpl @Inject constructor(private val repository: RestaurantRepository): GetRestaurantDetailsUseCase {
    override suspend fun call(locationId: Int): ResponseRestoDetails? {
        return repository.getRestaurantDetails(locationId)
    }
}