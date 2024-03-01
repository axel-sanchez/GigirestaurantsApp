package com.example.gigirestaurantsapp.data.models

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("location_id")
    val locationId: Int,
    val name: String,
    val distance: String,
    val rating: String,
    val bearing: String,
    @SerializedName("address_obj")
    val address: Address,
)

