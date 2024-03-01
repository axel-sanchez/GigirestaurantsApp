package com.example.gigirestaurantsapp.core

import android.app.Application
import com.example.gigirestaurantsapp.di.component.ApplicationComponent
import com.example.gigirestaurantsapp.di.component.DaggerApplicationComponent
import com.example.gigirestaurantsapp.di.module.ApplicationModule

/**
 * @author Axel Sanchez
 */
class MyApplication: Application() {
    val component: ApplicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(ApplicationModule(this))
        .build()
}