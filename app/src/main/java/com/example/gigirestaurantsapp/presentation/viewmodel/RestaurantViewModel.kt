package com.example.gigirestaurantsapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.gigirestaurantsapp.data.models.RestaurantDTO
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.domain.usecase.DislikeRestaurantUseCase
import com.example.gigirestaurantsapp.domain.usecase.GetNearbyRestaurantsUseCase
import com.example.gigirestaurantsapp.domain.usecase.GetRestaurantsBySearchUseCase
import com.example.gigirestaurantsapp.domain.usecase.LikeRestaurantUseCase
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class RestaurantViewModel(private val getNearbyRestaurantsUseCase: GetNearbyRestaurantsUseCase,
                          private val getRestaurantsBySearchUseCase: GetRestaurantsBySearchUseCase,
                          private val likeRestaurantUseCase: LikeRestaurantUseCase,
                          private val dislikeRestaurantUseCase: DislikeRestaurantUseCase
): ViewModel() {

    private val listData: MutableLiveData<RestaurantDTO> = MutableLiveData<RestaurantDTO>()
    private val listDataBySearch: MutableLiveData<RestaurantDTO> = MutableLiveData<RestaurantDTO>()

    private fun setListData(result: RestaurantDTO) {
        listData.postValue(result)
    }

    private fun setListDataBySearch(result: RestaurantDTO) {
        listDataBySearch.postValue(result)
    }

    fun getRestaurants(location: String) {
        viewModelScope.launch {
            setListData(getNearbyRestaurantsUseCase.call(location))
        }
    }

    fun getRestaurantsBySearch(query: String) {
        viewModelScope.launch {
            setListDataBySearch(getRestaurantsBySearchUseCase.call(query))
        }
    }

    fun getRestaurantsLiveData(): LiveData<RestaurantDTO> {
        return listData
    }

    fun getRestaurantsBySearchLiveData(): LiveData<RestaurantDTO> {
        return listDataBySearch
    }

    fun likeRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            restaurant.isLiked = true
            likeRestaurantUseCase.call(restaurant)
        }
    }

    fun dislikeRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            restaurant.isLiked = false
            dislikeRestaurantUseCase.call(restaurant)
        }
    }

    class RestaurantViewModelFactory(private val getNearbyRestaurantsUseCase: GetNearbyRestaurantsUseCase,
                                     private val getRestaurantsBySearchUseCase: GetRestaurantsBySearchUseCase,
                                     private val likeRestaurantUseCase: LikeRestaurantUseCase,
                                     private val dislikeRestaurantUseCase: DislikeRestaurantUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetNearbyRestaurantsUseCase::class.java, GetRestaurantsBySearchUseCase::class.java,
                LikeRestaurantUseCase::class.java, DislikeRestaurantUseCase::class.java)
                .newInstance(getNearbyRestaurantsUseCase, getRestaurantsBySearchUseCase, likeRestaurantUseCase, dislikeRestaurantUseCase)
        }
    }
}