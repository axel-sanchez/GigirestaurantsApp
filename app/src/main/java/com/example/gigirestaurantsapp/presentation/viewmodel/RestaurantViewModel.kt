package com.example.gigirestaurantsapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.gigirestaurantsapp.data.models.RestaurantDTO
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.domain.usecase.DeleteRestaurantUseCase
import com.example.gigirestaurantsapp.domain.usecase.GetNearbyRestaurantsUseCase
import com.example.gigirestaurantsapp.domain.usecase.SaveRestaurantUseCase
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class RestaurantViewModel(private val getNearbyRestaurantsUseCase: GetNearbyRestaurantsUseCase,
    private val saveRestaurantUseCase: SaveRestaurantUseCase,
    private val deleteRestaurantUseCase: DeleteRestaurantUseCase
): ViewModel() {

    private val listData: MutableLiveData<RestaurantDTO> = MutableLiveData<RestaurantDTO>()

    private fun setListData(result: RestaurantDTO) {
        listData.postValue(result)
    }

    fun getRestaurants(location: String) {
        viewModelScope.launch {
            setListData(getNearbyRestaurantsUseCase.call(location))
        }
    }

    fun getRestaurantsLiveData(): LiveData<RestaurantDTO> {
        return listData
    }

    fun favRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            saveRestaurantUseCase.call(restaurant)
        }
    }

    fun unFavRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            deleteRestaurantUseCase.call(restaurant)
        }
    }

    class RestaurantViewModelFactory(private val getNearbyRestaurantsUseCase: GetNearbyRestaurantsUseCase,
                                     private val saveRestaurantUseCase: SaveRestaurantUseCase,
                                     private val deleteRestaurantUseCase: DeleteRestaurantUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetNearbyRestaurantsUseCase::class.java, SaveRestaurantUseCase::class.java, DeleteRestaurantUseCase::class.java)
                .newInstance(getNearbyRestaurantsUseCase, saveRestaurantUseCase, deleteRestaurantUseCase)
        }
    }
}