package com.example.gigirestaurantsapp.di.component

import com.example.gigirestaurantsapp.di.module.ApplicationModule
import com.example.gigirestaurantsapp.presentation.view.FavoriteRestaurantsFragment
import com.example.gigirestaurantsapp.presentation.view.NearbyRestaurantsFragment
import com.example.gigirestaurantsapp.presentation.view.RestaurantDetailsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent{
    fun inject(nearbyRestaurantsFragment: NearbyRestaurantsFragment)
    fun inject(favoriteRestaurantsFragment: FavoriteRestaurantsFragment)
    fun inject(restaurantDetailsFragment: RestaurantDetailsFragment)
}