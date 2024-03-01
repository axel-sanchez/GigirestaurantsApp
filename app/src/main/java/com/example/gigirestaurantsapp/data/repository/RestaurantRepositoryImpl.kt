package com.example.gigirestaurantsapp.data.repository

import com.example.gigirestaurantsapp.data.models.ApiError
import com.example.gigirestaurantsapp.data.models.ResponseBody
import com.example.gigirestaurantsapp.data.source.RestaurantRemoteSource
import com.example.gigirestaurantsapp.domain.repository.RestaurantRepository
import com.example.gigirestaurantsapp.utils.Constants.ApiError.*
import com.example.gigirestaurantsapp.utils.Constants.GENERIC_CODE
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
class RestaurantRepositoryImpl @Inject constructor(
    private val restaurantRemoteSource: RestaurantRemoteSource
    //, private val productLocalSource: ProductLocalSource
) : RestaurantRepository {

    override suspend fun getNearbyRestaurants(location: String): ResponseBody {
        return restaurantRemoteSource.getNearbyRestaurants(location).value ?: ResponseBody(error = ApiError(
            GENERIC_ERROR.text, GENERIC_ERROR.text, GENERIC_CODE))

        /*val localProducts = getLocalProducts()
        if (localProducts.isNotEmpty()) {
            return ResponseBody(restaurant = localProducts)
        }

        val remoteDataProducts = getRemoteProducts(query)

        if (!remoteDataProducts.products.isNullOrEmpty()) {
            addProductsInDB(remoteDataProducts.products, query)
        }

        return remoteDataProducts*/
    }

    /*override suspend fun getProductDetails(idProduct: String): ProductDetails {
        val localProductDetails = productLocalSource.getProductDetails(idProduct)
        if (localProductDetails?.description != null) {
            return localProductDetails
        }

        val remoteDataProductDetails = getRemoteProductDetails(idProduct)

        if (remoteDataProductDetails.apiError == null) {
            addProductDetailsInDB(remoteDataProductDetails)
        }

        return remoteDataProductDetails
    }

    override suspend fun getLocalProducts(query: String): List<Product?> {
        return productLocalSource.getProductBySearch(query)
    }

    override suspend fun getRemoteProducts(query: String): DataProducts {
        return restaurantRemoteSource.getProducts(query).value ?: DataProducts(apiError = GENERIC)
    }

    private suspend fun getRemoteProductDetails(idProduct: String): ProductDetails{
        val description = restaurantRemoteSource.getDescription(idProduct).value
        val productDetailsLiveData = restaurantRemoteSource.getProductDetails(idProduct)
        productDetailsLiveData.value?.description = description
        return productDetailsLiveData.value ?: ProductDetails(id = "", apiError = GENERIC)
    }

    private suspend fun addProductsInDB(result: List<Product?>, query: String) {
        result.forEach { product ->
            product?.search = query
            productLocalSource.insertProduct(product)
        }
    }

    private suspend fun addProductDetailsInDB(productDetails: ProductDetails?) {
        productLocalSource.insertProductDetails(productDetails)
    }*/
}