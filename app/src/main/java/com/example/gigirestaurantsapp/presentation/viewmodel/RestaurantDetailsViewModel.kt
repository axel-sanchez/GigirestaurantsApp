package com.example.gigirestaurantsapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gigirestaurantsapp.data.models.ResponseRestoDetails
import com.example.gigirestaurantsapp.domain.usecase.GetRestaurantDetailsUseCase
import kotlinx.coroutines.launch

class RestaurantDetailsViewModel(private val getRestaurantDetailsUseCase: GetRestaurantDetailsUseCase): ViewModel() {

    private val listData: MutableLiveData<ResponseRestoDetails?> = MutableLiveData<ResponseRestoDetails?>()

    private fun setListData(result: ResponseRestoDetails?) {
        listData.postValue(result)
    }

    fun getRestaurantDetails(locationId: Int) {
        viewModelScope.launch {
            setListData(getRestaurantDetailsUseCase.call(locationId))
        }
    }

    fun getRestaurantDetailsLiveData(): LiveData<ResponseRestoDetails?> {
        return listData
    }

    class RestaurantDetailsViewModelFactory(private val getRestaurantDetailsUseCase: GetRestaurantDetailsUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetRestaurantDetailsUseCase::class.java)
                .newInstance(getRestaurantDetailsUseCase)
        }
    }
}