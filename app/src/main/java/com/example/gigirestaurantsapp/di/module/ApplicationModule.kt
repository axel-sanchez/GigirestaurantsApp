package com.example.gigirestaurantsapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.gigirestaurantsapp.data.repository.RestaurantRepositoryImpl
import com.example.gigirestaurantsapp.data.service.ApiClient
import com.example.gigirestaurantsapp.data.service.ApiServiceRestaurant
import com.example.gigirestaurantsapp.data.source.RestaurantRemoteSource
import com.example.gigirestaurantsapp.data.source.RestaurantRemoteSourceImpl
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import com.example.gigirestaurantsapp.domain.usecase.GetNearbyRestaurantsUseCase
import com.example.gigirestaurantsapp.domain.usecase.GetNearbyRestaurantsUseCaseImpl
import com.example.gigirestaurantsapp.utils.Constants.BASE_URL
import com.example.gigirestaurantsapp.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Module
class ApplicationModule(private val context: Context){

    @Provides
    @Singleton
    fun provideRestaurantRemoteSource(restaurantRemoteSource: RestaurantRemoteSourceImpl): RestaurantRemoteSource = restaurantRemoteSource

    @Provides
    @Singleton
    fun provideRestaurantRepository(restaurantRemoteSource: RestaurantRemoteSource): RestaurantRepository{
        //return if (isRunningTest) FakeRepository()
         return RestaurantRepositoryImpl(restaurantRemoteSource)
    }

    @Provides
    @Singleton
    fun provideGetNearbyRestaurantsUseCase(getNearbyRestaurantsUseCase: GetNearbyRestaurantsUseCaseImpl): GetNearbyRestaurantsUseCase = getNearbyRestaurantsUseCase

    @Provides
    @Singleton
    fun provideApiServiceRestaurant(): ApiServiceRestaurant {
        return ApiClient.Builder<ApiServiceRestaurant>()
            .setBaseUrl(BASE_URL)
            .setApiService(ApiServiceRestaurant::class.java)
            .build()
    }

    /*@Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        return Room
            .databaseBuilder(context, Database::class.java, "MercadoLibreDB.db4")
            .build()
    }

    @Provides
    @Singleton
    fun provideProductLocalSource(database: Database): ProductLocalSource {
        return ProductLocalSourceImpl(database.productDao())
    }*/

    @Provides
    @Singleton
    fun provideNetworkHelper(context: Context) = NetworkHelper(context)

    @Provides
    @Singleton
    fun provideContext(): Context = context
}