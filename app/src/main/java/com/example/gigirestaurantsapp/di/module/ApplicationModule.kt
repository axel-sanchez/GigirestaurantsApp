package com.example.gigirestaurantsapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.gigirestaurantsapp.data.repository.RestaurantRepositoryImpl
import com.example.gigirestaurantsapp.data.room.Database
import com.example.gigirestaurantsapp.data.service.ApiClient
import com.example.gigirestaurantsapp.data.service.ApiServiceRestaurant
import com.example.gigirestaurantsapp.data.source.RestaurantLocalSource
import com.example.gigirestaurantsapp.data.source.RestaurantLocalSourceImpl
import com.example.gigirestaurantsapp.data.source.RestaurantRemoteSource
import com.example.gigirestaurantsapp.data.source.RestaurantRemoteSourceImpl
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import com.example.gigirestaurantsapp.domain.usecase.DislikeRestaurantUseCase
import com.example.gigirestaurantsapp.domain.usecase.DislikeRestaurantUseCaseImpl
import com.example.gigirestaurantsapp.domain.usecase.GetFavoriteRestaurantsUseCase
import com.example.gigirestaurantsapp.domain.usecase.GetFavoriteRestaurantsUseCaseImpl
import com.example.gigirestaurantsapp.domain.usecase.GetNearbyRestaurantsUseCase
import com.example.gigirestaurantsapp.domain.usecase.GetNearbyRestaurantsUseCaseImpl
import com.example.gigirestaurantsapp.domain.usecase.GetRestaurantDetailsUseCase
import com.example.gigirestaurantsapp.domain.usecase.GetRestaurantDetailsUseCaseImpl
import com.example.gigirestaurantsapp.domain.usecase.LikeRestaurantUseCase
import com.example.gigirestaurantsapp.domain.usecase.LikeRestaurantUseCaseImpl
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
    fun provideRestaurantRepository(restaurantRemoteSource: RestaurantRemoteSource, restaurantLocalSource: RestaurantLocalSource): RestaurantRepository{
         return RestaurantRepositoryImpl(restaurantRemoteSource, restaurantLocalSource)
    }

    @Provides
    @Singleton
    fun provideGetNearbyRestaurantsUseCase(getNearbyRestaurantsUseCase: GetNearbyRestaurantsUseCaseImpl): GetNearbyRestaurantsUseCase = getNearbyRestaurantsUseCase

    @Provides
    @Singleton
    fun provideGetRestaurantDetailsUseCase(getRestaurantDetailsUseCase: GetRestaurantDetailsUseCaseImpl): GetRestaurantDetailsUseCase = getRestaurantDetailsUseCase

    @Provides
    @Singleton
    fun provideGetFavoriteRestaurantsUseCase(getFavoriteRestaurantsUseCase: GetFavoriteRestaurantsUseCaseImpl): GetFavoriteRestaurantsUseCase = getFavoriteRestaurantsUseCase

    @Provides
    @Singleton
    fun provideSaveRestaurantUseCase(saveRestaurantUseCase: LikeRestaurantUseCaseImpl): LikeRestaurantUseCase = saveRestaurantUseCase

    @Provides
    @Singleton
    fun provideDeleteRestaurantUseCase(deleteRestaurantUseCase: DislikeRestaurantUseCaseImpl): DislikeRestaurantUseCase = deleteRestaurantUseCase

    @Provides
    @Singleton
    fun provideApiServiceRestaurant(): ApiServiceRestaurant {
        return ApiClient.Builder<ApiServiceRestaurant>()
            .setBaseUrl(BASE_URL)
            .setApiService(ApiServiceRestaurant::class.java)
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        return Room
            .databaseBuilder(context, Database::class.java, "GigirestaurantsDB.db3")
            .build()
    }

    @Provides
    @Singleton
    fun provideRestaurantLocalSource(database: Database): RestaurantLocalSource {
        return RestaurantLocalSourceImpl(database.restaurantDao())
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(context: Context) = NetworkHelper(context)

    @Provides
    @Singleton
    fun provideContext(): Context = context
}