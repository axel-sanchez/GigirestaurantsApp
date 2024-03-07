package com.example.gigirestaurantsapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.domain.usecase.GetLikedRestaurantsUseCase

class LikedRestaurantsViewModel(
    private val getLikedRestaurantsUseCase: GetLikedRestaurantsUseCase
) :
    ViewModel() {

    fun getRestaurantsLiveData(): LiveData<List<Restaurant>> {
        return getLikedRestaurantsUseCase.call()
    }

    class LikedRestaurantsViewModelFactory(
        private val getLikedRestaurantsUseCase: GetLikedRestaurantsUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(
                GetLikedRestaurantsUseCase::class.java,
            )
                .newInstance(getLikedRestaurantsUseCase)
        }
    }
}