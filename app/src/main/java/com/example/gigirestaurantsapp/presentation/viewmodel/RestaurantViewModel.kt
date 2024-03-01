package com.example.gigirestaurantsapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.gigirestaurantsapp.data.models.ResponseBody
import com.example.gigirestaurantsapp.domain.usecase.GetNearbyRestaurantsUseCase
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class RestaurantViewModel(private val getNearbyRestaurantsUseCase: GetNearbyRestaurantsUseCase): ViewModel() {

    private val listData: MutableLiveData<ResponseBody> by lazy {
        MutableLiveData<ResponseBody>().also {
            getRestaurants()
        }
    }

    private fun setListData(result: ResponseBody) {
        listData.postValue(result)
    }

    private fun getRestaurants() {
        viewModelScope.launch {
            setListData(getNearbyRestaurantsUseCase.call())
        }
    }

    fun getRestaurantsLiveData(): LiveData<ResponseBody> {
        return listData
    }

    class RestaurantViewModelFactory(private val getNearbyRestaurantsUseCase: GetNearbyRestaurantsUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetNearbyRestaurantsUseCase::class.java).newInstance(getNearbyRestaurantsUseCase)
        }
    }
}