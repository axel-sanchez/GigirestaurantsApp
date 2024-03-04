package com.example.gigirestaurantsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Restaurant(
    @SerializedName("location_id")
    @PrimaryKey val locationId: Int,
    val name: String? = null,
    val distance: String? = null,
    val rating: String? = null,
    val bearing: String? = null,
    @SerializedName("address_obj")
    val address: Address? = null,
)

