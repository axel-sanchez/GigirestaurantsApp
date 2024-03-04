package com.example.gigirestaurantsapp.data.room

import androidx.room.TypeConverter
import com.example.gigirestaurantsapp.data.models.Address
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.google.gson.Gson

/**
 * @author Axel Sanchez
 */
class Converters{
    private val gson: Gson = Gson()

    @TypeConverter
    fun fromRestaurant(restaurant: Restaurant?): String? {
        restaurant?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toRestaurant(restaurant: String?): Restaurant? {
        restaurant?.let {
            return gson.fromJson(it, Restaurant::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromAddress(address: Address?): String? {
        address?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toAddress(address: String?): Address? {
        address?.let {
            return gson.fromJson(it, Address::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromRestaurantList(list: List<Restaurant?>?): String? {
        var response = ""
        list?.let {
            for (i in list.indices) {
                response += if (i == 0) fromRestaurant(it[i])
                else ";${fromRestaurant(it[i])}"
            }
        } ?: return null
        return response
    }

    @TypeConverter
    fun toRestaurantList(concat: String?): List<Restaurant?>? {
        val list = concat?.split(";")
        list?.let {
            return it.map { str -> toRestaurant(str) }
        } ?: return null
    }
}