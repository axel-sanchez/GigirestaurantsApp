package com.example.gigirestaurantsapp.data.models


data class ResponseRestoDetails(
    val address_obj: AddressObj? = null,
    val amenities: List<String>? = null,
    val ancestors: List<Ancestor>? = null,
    val awards: List<Any>? = null,
    val category: Category? = null,
    val description: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val latitude: String? = null,
    val location_id: String? = null,
    val longitude: String? = null,
    val name: String? = null,
    val neighborhood_info: List<Any>? = null,
    val num_reviews: String? = null,
    val photo_count: String? = null,
    val price_level: String? = null,
    val ranking_data: RankingData? = null,
    val rating: String? = null,
    val rating_image_url: String? = null,
    val review_rating_count: ReviewRatingCount? = null,
    val see_all_photos: String? = null,
    val styles: List<String>? = null,
    val subcategory: List<Subcategory>? = null,
    val subratings: Subratings? = null,
    val timezone: String? = null,
    val trip_types: List<TripType>? = null,
    val web_url: String? = null,
    val write_review: String? = null
) {
    data class AddressObj(
        val address_string: String? = null,
        val city: String? = null,
        val country: String? = null,
        val postalcode: String? = null,
        val state: String? = null,
        val street1: String? = null
    )

    data class Ancestor(
        val level: String? = null,
        val location_id: String? = null,
        val name: String? = null
    )

    data class Category(
        val localized_name: String? = null,
        val name: String? = null
    )

    data class RankingData(
        val geo_location_id: String? = null,
        val geo_location_name: String? = null,
        val ranking: String? = null,
        val ranking_out_of: String? = null,
        val ranking_string: String? = null
    )

    data class ReviewRatingCount(
        val `1`: String? = null,
        val `2`: String? = null,
        val `3`: String? = null,
        val `4`: String? = null,
        val `5`: String? = null
    )

    data class Subcategory(
        val localized_name: String? = null,
        val name: String? = null
    )

    data class Subratings(
        val `0`: X0? = null,
        val `1`: X1? = null,
        val `2`: X2? = null,
        val `3`: X3? = null,
        val `4`: X4? = null,
        val `5`: X5? = null
    ) {
        data class X0(
            val localized_name: String? = null,
            val name: String? = null,
            val rating_image_url: String? = null,
            val value: String? = null
        )

        data class X1(
            val localized_name: String? = null,
            val name: String? = null,
            val rating_image_url: String? = null,
            val value: String? = null
        )

        data class X2(
            val localized_name: String? = null,
            val name: String? = null,
            val rating_image_url: String? = null,
            val value: String? = null
        )

        data class X3(
            val localized_name: String? = null,
            val name: String? = null,
            val rating_image_url: String? = null,
            val value: String? = null
        )

        data class X4(
            val localized_name: String? = null,
            val name: String? = null,
            val rating_image_url: String? = null,
            val value: String? = null
        )

        data class X5(
            val localized_name: String? = null,
            val name: String? = null,
            val rating_image_url: String? = null,
            val value: String? = null
        )
    }

    data class TripType(
        val localized_name: String? = null,
        val name: String? = null,
        val value: String? = null
    )
}