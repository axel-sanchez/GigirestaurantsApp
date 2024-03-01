package com.example.gigirestaurantsapp.data.models

import com.google.gson.annotations.SerializedName

data class Address(
    val street1: String,
    val street2: String,
    val city: String,
    val state: String,
    val country: String,
    @SerializedName("postalcode")
    val postalCode: String,
    @SerializedName("address_string")
    val addressString: String,
    val phone: String,
    val latitude: String,
    val longitude: String
)
