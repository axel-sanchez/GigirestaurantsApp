package com.example.gigirestaurantsapp.utils

import com.example.gigirestaurantsapp.data.repository.FakeRepository
import junit.framework.TestCase.assertEquals
import org.junit.Test

class LocationHelperTest{

    private val locationHelper = LocationHelper()

    @Test
    fun should_get_latitude_from_location_string(){
        val latitude = locationHelper.getLatitudeFromLocationString(FakeRepository.LOCATION)
        assertEquals(latitude, FakeRepository.LATITUDE)
    }

    @Test
    fun should_get_longitude_from_location_string(){
        val longitude = locationHelper.getLongitudeFromLocationString(FakeRepository.LOCATION)
        assertEquals(longitude, FakeRepository.LONGITUDE)
    }

    @Test
    fun should_get_empty_latitude_when_location_is_empty(){
        val latitude = locationHelper.getLatitudeFromLocationString("")
        assertEquals(latitude, "")
    }

    @Test
    fun should_get_empty_latitude_when_location_has_only_latitude(){
        val latitude = locationHelper.getLatitudeFromLocationString("-31.418119675147636")
        assertEquals(latitude, "")
    }

    @Test
    fun should_get_empty_latitude_when_location_has_not_a_comma(){
        val latitude = locationHelper.getLatitudeFromLocationString("-31.418119675147636 -64.49176343201465")
        assertEquals(latitude, "")
    }
}