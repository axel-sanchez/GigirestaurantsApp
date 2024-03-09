package com.example.gigirestaurantsapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gigirestaurantsapp.R
import com.example.gigirestaurantsapp.core.MyApplication
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.databinding.FragmentLikedRestaurantsBinding
import com.example.gigirestaurantsapp.domain.usecase.GetLikedRestaurantsUseCase
import com.example.gigirestaurantsapp.presentation.adapter.LikedRestaurantsAdapter
import com.example.gigirestaurantsapp.presentation.viewmodel.LikedRestaurantsViewModel
import com.example.gigirestaurantsapp.utils.Constants
import com.example.gigirestaurantsapp.utils.hide
import com.example.gigirestaurantsapp.utils.show
import javax.inject.Inject

class LikedRestaurantsFragment : Fragment() {

    @Inject
    lateinit var getLikedRestaurantsUseCase: GetLikedRestaurantsUseCase

    private val viewModel: LikedRestaurantsViewModel by viewModels(
        factoryProducer = {
            LikedRestaurantsViewModel.LikedRestaurantsViewModelFactory(
                getLikedRestaurantsUseCase
            )
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).component.inject(this)
    }

    private var fragmentLikedRestaurantsBinding: FragmentLikedRestaurantsBinding? = null
    private val binding get() = fragmentLikedRestaurantsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentLikedRestaurantsBinding =
            FragmentLikedRestaurantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentLikedRestaurantsBinding = null
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
                rvLikedRestaurants.hide()
                tvErrorText.text = Constants.ApiError.EMPTY_LIKED_RESTAURANTS.text
                cvEmptyState.show()
            } else {
                rvLikedRestaurants.show()
                cvEmptyState.hide()
                setAdapter(restaurants)
            }
            cpiLoading.hide()
        }
    }

    private fun setAdapter(restaurants: List<Restaurant>) {
        val restaurantsAdapter = LikedRestaurantsAdapter(
            restaurants.toMutableList(),
            itemClick
        )
        with(binding.rvLikedRestaurants) {
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