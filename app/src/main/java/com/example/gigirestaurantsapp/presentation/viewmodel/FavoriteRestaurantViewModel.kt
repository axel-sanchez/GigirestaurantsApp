package com.example.gigirestaurantsapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.domain.usecase.GetFavoriteRestaurantsUseCase

class FavoriteRestaurantViewModel(
    private val getFavoriteRestaurantsUseCase: GetFavoriteRestaurantsUseCase
) :
    ViewModel() {

    fun getRestaurantsLiveData(): LiveData<List<Restaurant>> {
        return getFavoriteRestaurantsUseCase.call()
    }

    class FavoriteRestaurantViewModelFactory(
        private val getFavoriteRestaurantsUseCase: GetFavoriteRestaurantsUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(
                GetFavoriteRestaurantsUseCase::class.java,
            )
                .newInstance(getFavoriteRestaurantsUseCase)
        }
    }
}