package com.example.gigirestaurantsapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.domain.usecase.DeleteRestaurantUseCase
import com.example.gigirestaurantsapp.domain.usecase.GetFavoriteRestaurantsUseCase
import kotlinx.coroutines.launch

class FavoriteRestaurantViewModel(private val getFavoriteRestaurantsUseCase: GetFavoriteRestaurantsUseCase,
                                  private val deleteRestaurantUseCase: DeleteRestaurantUseCase
) :
    ViewModel() {

    private val listData: MutableLiveData<List<Restaurant>> by lazy {
        MutableLiveData<List<Restaurant>>().also {
            getRestaurants()
        }
    }

    private fun setListData(result: List<Restaurant>) {
        listData.postValue(result)
    }

    private fun getRestaurants() {
        viewModelScope.launch {
            setListData(getFavoriteRestaurantsUseCase.call())
        }
    }

    fun getRestaurantsLiveData(): LiveData<List<Restaurant>> {
        return listData
    }

    fun unFavRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            deleteRestaurantUseCase.call(restaurant)
        }
    }

    class FavoriteRestaurantViewModelFactory(
        private val getFavoriteRestaurantsUseCase: GetFavoriteRestaurantsUseCase,
        private val deleteRestaurantUseCase: DeleteRestaurantUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetFavoriteRestaurantsUseCase::class.java, DeleteRestaurantUseCase::class.java)
                .newInstance(getFavoriteRestaurantsUseCase, deleteRestaurantUseCase)
        }
    }
}