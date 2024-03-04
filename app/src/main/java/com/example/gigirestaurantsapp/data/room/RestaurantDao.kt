package com.example.gigirestaurantsapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gigirestaurantsapp.data.models.Restaurant

/**
 * @author Axel Sanchez
 */
@Dao
interface RestaurantDao {
    @Query("SELECT * FROM Restaurant where locationId = :idRestaurant")
    suspend fun getRestaurant(idRestaurant: Int): Restaurant

    @Query("SELECT * FROM Restaurant")
    suspend fun getFavoriteRestaurants(): List<Restaurant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: Restaurant)

    @Delete
    suspend fun deleteRestaurant(restaurant: Restaurant)
}