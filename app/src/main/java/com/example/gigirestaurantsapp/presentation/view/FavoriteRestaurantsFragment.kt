package com.example.gigirestaurantsapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gigirestaurantsapp.R
import com.example.gigirestaurantsapp.core.MyApplication
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.databinding.FragmentFavoriteRestaurantsBinding
import com.example.gigirestaurantsapp.domain.usecase.GetFavoriteRestaurantsUseCase
import com.example.gigirestaurantsapp.presentation.adapter.FavoriteRestaurantsAdapter
import com.example.gigirestaurantsapp.presentation.viewmodel.FavoriteRestaurantViewModel
import com.example.gigirestaurantsapp.utils.Constants
import com.example.gigirestaurantsapp.utils.hide
import com.example.gigirestaurantsapp.utils.show
import javax.inject.Inject

class FavoriteRestaurantsFragment : Fragment() {

    @Inject
    lateinit var getFavoriteRestaurantsUseCase: GetFavoriteRestaurantsUseCase

    private val viewModel: FavoriteRestaurantViewModel by viewModels(
        factoryProducer = {
            FavoriteRestaurantViewModel.FavoriteRestaurantViewModelFactory(
                getFavoriteRestaurantsUseCase
            )
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).component.inject(this)
    }

    private var fragmentFavoriteRestaurantsBinding: FragmentFavoriteRestaurantsBinding? = null
    private val binding get() = fragmentFavoriteRestaurantsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoriteRestaurantsBinding =
            FragmentFavoriteRestaurantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentFavoriteRestaurantsBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRestaurantsLiveData().observe(viewLifecycleOwner) { response ->
            updateView(response)
        }
    }

    private fun updateView(restaurants: List<Restaurant>) {
        with(binding) {

            if (restaurants.isEmpty()) {
                rvRestaurants.hide()
                tvErrorText.text = Constants.ApiError.EMPTY_FAVORITE_RESTAURANTS.text
                cvEmptyState.show()
            } else {
                rvRestaurants.show()
                cvEmptyState.hide()
                setAdapter(restaurants)
            }
            cpiLoading.hide()
        }
    }

    private fun setAdapter(restaurants: List<Restaurant>) {
        val restaurantsAdapter = FavoriteRestaurantsAdapter(
            restaurants.toMutableList(),
            itemClick
        )
        with(binding.rvRestaurants) {
            layoutManager = LinearLayoutManager(context)
            adapter = restaurantsAdapter
        }
    }

    private val itemClick = { restaurant: Restaurant? ->
        restaurant?.let {
            val bundle = bundleOf(Constants.LOCATION_ID to it.locationId)
            findNavController().navigate(R.id.action_mainFragment_to_restaurantDetailsFragment, bundle, null, null)
        }
    }
}