package com.example.gigirestaurantsapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import javax.inject.Inject
import javax.inject.Singleton

interface GetFavoriteRestaurantsUseCase{
    fun call(): LiveData<List<Restaurant>>
}

@Singleton
class GetFavoriteRestaurantsUseCaseImpl @Inject constructor(private val repository: RestaurantRepository): GetFavoriteRestaurantsUseCase {
    override fun call(): LiveData<List<Restaurant>> {
        return repository.getFavRestaurantsLiveData()
    }
}