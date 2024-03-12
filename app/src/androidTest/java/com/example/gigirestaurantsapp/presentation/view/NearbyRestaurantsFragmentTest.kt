package com.example.gigirestaurantsapp.presentation.view

import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gigirestaurantsapp.data.repository.FakeRepository
import com.example.gigirestaurantsapp.R
import com.example.gigirestaurantsapp.presentation.adapter.RestaurantAdapter
import com.example.gigirestaurantsapp.utils.RecyclerViewItemCountAssertion
import com.example.gigirestaurantsapp.utils.inThePosition
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NearbyRestaurantsFragmentTest{

    private val repository = FakeRepository()

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun should_show_recyclerview_and_hide_progress_and_message() {
        Espresso.onView(withId(R.id.rvRestaurants)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.cpiLoading)).check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
        Espresso.onView(withId(R.id.cvEmptyState)).check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun should_recyclerview_has_five_elements() {
        Espresso.onView(withId(R.id.rvRestaurants)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rvRestaurants)).check(RecyclerViewItemCountAssertion(5))
    }

    @Test
    fun should_show_recyclerview_when_press_back_from_details_fragment() {
        Espresso.onView(withId(R.id.rvRestaurants)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rvRestaurants)).perform(RecyclerViewActions.scrollToPosition<RestaurantAdapter.ViewHolder>(0))
        Espresso.onView(withId(R.id.rvRestaurants)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.pressBack()
        Espresso.onView(withId(R.id.rvRestaurants)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.cpiLoading)).check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
        Espresso.onView(withId(R.id.cvEmptyState)).check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun should_verify_that_first_element_is_correct(){
        Espresso.onView(withId(R.id.rvRestaurants))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rvRestaurants))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        Espresso.onView(withId(R.id.rvRestaurants)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(withId(R.id.tvName))
            .check(ViewAssertions.matches(withText(repository.restaurant1.name)))
    }
}