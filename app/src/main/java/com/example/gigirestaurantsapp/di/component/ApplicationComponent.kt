package com.example.gigirestaurantsapp.di.component

import com.example.gigirestaurantsapp.di.module.ApplicationModule
import com.example.gigirestaurantsapp.presentation.FavoriteRestaurantsFragment
import com.example.gigirestaurantsapp.presentation.NearbyRestaurantsFragment
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
}