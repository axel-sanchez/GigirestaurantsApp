package com.example.gigirestaurantsapp.presentation.view

import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gigirestaurantsapp.data.repository.FakeRepository
import com.example.gigirestaurantsapp.R
import com.example.gigirestaurantsapp.data.repository.FakeRepository.Companion.QUERY_PIZZA
import com.example.gigirestaurantsapp.presentation.adapter.RestaurantAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RestaurantDetailsFragmentTest{

    private val repository = FakeRepository()

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.rvRestaurants)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                ViewActions.click()
            ))
    }

    @Test
    fun should_show_product_title_and_description() {
        onView(withId(R.id.tvName)).check(ViewAssertions.matches(withText(repository.restaurant1.name)))
        onView(withId(R.id.tvDescription)).check(ViewAssertions.matches(withText(repository.restaurant1.description)))
    }
}