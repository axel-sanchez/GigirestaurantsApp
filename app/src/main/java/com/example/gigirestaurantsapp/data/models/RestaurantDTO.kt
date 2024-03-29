package com.example.gigirestaurantsapp.data.models

import com.google.gson.annotations.SerializedName

data class RestaurantDTO(
    @SerializedName("data")
    val restaurants: List<Restaurant?>? = null,
    val error: ApiError? = null
)