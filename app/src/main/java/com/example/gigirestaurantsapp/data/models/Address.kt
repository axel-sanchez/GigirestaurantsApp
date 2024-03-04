package com.example.gigirestaurantsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Address(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val street1: String? = null,
    val street2: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    @SerializedName("postalcode")
    val postalCode: String? = null,
    @SerializedName("address_string")
    val addressString: String? = null,
    val phone: String? = null,
    val latitude: String? = null,
    val longitude: String? = null
)
