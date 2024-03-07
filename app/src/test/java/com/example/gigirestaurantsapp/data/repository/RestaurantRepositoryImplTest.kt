package com.example.gigirestaurantsapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.gigirestaurantsapp.data.repository.FakeRepository.Companion.LOCATION
import com.example.gigirestaurantsapp.data.repository.FakeRepository.Companion.QUERY
import com.example.gigirestaurantsapp.data.source.RestaurantLocalSource
import com.example.gigirestaurantsapp.data.source.RestaurantRemoteSource
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import com.example.gigirestaurantsapp.utils.LocationHelper
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mockito


class RestaurantRepositoryImplTest{

    private val fakeRepository = FakeRepository()
    private val locationHelper = LocationHelper()

    private val restaurantRemoteSource: RestaurantRemoteSource =
        Mockito.mock(RestaurantRemoteSource::class.java)
    private val restaurantLocalSource: RestaurantLocalSource =
        Mockito.mock(RestaurantLocalSource::class.java)
    private val restaurantRepository: RestaurantRepository = RestaurantRepositoryImpl(restaurantRemoteSource, restaurantLocalSource, locationHelper)

    @Test
    fun should_calls_to_getRemoteRestaurants_when_there_are_not_local_restaurants(){
        runBlocking {
            val mutableListData = MutableLiveData(fakeRepository.getRemoteProducts())
            BDDMockito.given(restaurantRemoteSource.getNearbyRestaurants(LOCATION)).willReturn(mutableListData)
            BDDMockito.given(restaurantLocalSource.getNearbyRestaurants(LOCATION)).willReturn(listOf())
            restaurantRepository.getNearbyRestaurants(LOCATION)
            Mockito.verify(restaurantRemoteSource).getNearbyRestaurants(LOCATION)
        }
    }

    @Test
    fun should_not_calls_to_getRemoteRestaurants_when_there_are_local_restaurants(){
        runBlocking {
            BDDMockito.given(restaurantLocalSource.getRestaurantsBySearch(QUERY)).willReturn(listOf(fakeRepository.restaurant3))
            restaurantRepository.getRestaurantsBySearch(QUERY)
            Mockito.verify(restaurantRemoteSource, BDDMockito.never()).getRestaurantsBySearch(QUERY)
        }
    }
}