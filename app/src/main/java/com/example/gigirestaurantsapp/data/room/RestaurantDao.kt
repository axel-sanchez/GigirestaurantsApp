package com.example.gigirestaurantsapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gigirestaurantsapp.data.models.Restaurant

/**
 * @author Axel Sanchez
 */
@Dao
interface RestaurantDao {
    @Query("SELECT * FROM Restaurant where locationId = :idRestaurant")
    suspend fun getRestaurant(idRestaurant: Int): Restaurant

    @Query("SELECT * FROM Restaurant WHERE isLiked == 1")
    fun getLikedRestaurantsLiveData(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM Restaurant WHERE latitude = :latitude AND longitude = :longitude")
    suspend fun getNearbyRestaurants(latitude: String, longitude: String): List<Restaurant>

    @Query("SELECT * FROM Restaurant WHERE `query` = :query")
    suspend fun getRestaurantsBySearch(query: String): List<Restaurant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: Restaurant): Long

    @Update
    suspend fun updateRestaurant(restaurant: Restaurant)

    @Delete
    suspend fun deleteRestaurant(restaurant: Restaurant)
}