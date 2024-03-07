package com.example.gigirestaurantsapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import javax.inject.Inject
import javax.inject.Singleton

interface GetLikedRestaurantsUseCase{
    fun call(): LiveData<List<Restaurant>>
}

@Singleton
class GetLikedRestaurantsUseCaseImpl @Inject constructor(private val repository: RestaurantRepository): GetLikedRestaurantsUseCase {
    override fun call(): LiveData<List<Restaurant>> {
        return repository.getLikedRestaurantsLiveData()
    }
}