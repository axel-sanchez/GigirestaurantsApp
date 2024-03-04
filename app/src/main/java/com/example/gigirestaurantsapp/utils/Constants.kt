package com.example.gigirestaurantsapp.utils

object Constants {
    //Tab
    const val NEARBY_RESTAURANTS = "Restaurantes Cercanos"
    const val FAVORITE_RESTAURANTS = "Restaurantes Favoritos"

    //Api
    const val BASE_URL = "https://api.content.tripadvisor.com/api/v1/location/"
    const val GET_NEARBY_RESTAURANTS = "nearby_search"
    const val API_KEY = "D35A887E19AE4D5687F8D4F0E36D9BD2"

    enum class ApiError(var text: String) {
        GENERIC_ERROR("Hubo un error al obtener los restaurantes"),
        EMPTY_RESTAURANTS("No se obtuvo ningún restaurante"),
        EMPTY_FAVORITE_RESTAURANTS("No ha indicado que le gusta ningún restaurante"),
        NETWORK_ERROR("Hubo un error en la conexión de internet")
    }

    const val NETWORK_ERROR_CODE = 1
    const val GENERIC_CODE = 2
}