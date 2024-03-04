package com.example.gigirestaurantsapp.data.room

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Database
import com.example.gigirestaurantsapp.data.models.Address
import com.example.gigirestaurantsapp.data.models.Restaurant

/**
 * @author Axel Sanchez
 */
@Database(
    entities = [Restaurant::class, Address::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
}