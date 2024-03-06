package com.example.gigirestaurantsapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gigirestaurantsapp.core.MyApplication
import com.example.gigirestaurantsapp.data.models.ResponseRestoDetails
import com.example.gigirestaurantsapp.databinding.FragmentRestaurantDetailsBinding
import com.example.gigirestaurantsapp.domain.usecase.GetRestaurantDetailsUseCase
import com.example.gigirestaurantsapp.presentation.viewmodel.RestaurantDetailsViewModel
import com.example.gigirestaurantsapp.utils.Constants.LOCATION_ID
import com.example.gigirestaurantsapp.utils.hide
import com.example.gigirestaurantsapp.utils.show
import javax.inject.Inject

class RestaurantDetailsFragment : Fragment() {

    private var locationId: Int = -1

    @Inject
    lateinit var getRestaurantDetailsUseCase: GetRestaurantDetailsUseCase

    private val viewModel: RestaurantDetailsViewModel by viewModels(
        factoryProducer = {
            RestaurantDetailsViewModel.RestaurantDetailsViewModelFactory(
                getRestaurantDetailsUseCase
            )
        }
    )

    private var fragmentRestaurantDetailsBinding: FragmentRestaurantDetailsBinding? = null
    private val binding get() = fragmentRestaurantDetailsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentRestaurantDetailsBinding =
            FragmentRestaurantDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentRestaurantDetailsBinding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationId = arguments?.getInt(LOCATION_ID) ?: -1

        viewModel.getRestaurantDetails(locationId)

        viewModel.getRestaurantDetailsLiveData().observe(viewLifecycleOwner) { response ->
            updateView(response)
        }
    }

    private fun updateView(response: ResponseRestoDetails?) {
        response?.let { restaurant ->
            with(binding) {
                if (restaurant.name.isNullOrEmpty()){
                    tvName.hide()
                    tvTitleName.hide()
                } else tvName.text = restaurant.name

                if (restaurant.description.isNullOrEmpty()){
                    tvDescription.hide()
                    tvTitleDescription.hide()
                } else tvDescription.text = restaurant.description

                if (restaurant.email.isNullOrEmpty()){
                    tvEmail.hide()
                    tvTitleEmail.hide()
                } else tvEmail.text = restaurant.email

                if (restaurant.phone.isNullOrEmpty()){
                    tvPhone.hide()
                    tvTitlePhone.hide()
                } else tvPhone.text = restaurant.phone

                if (restaurant.rating.isNullOrEmpty()){
                    tvRating.hide()
                    tvTitleRating.hide()
                } else tvRating.text = restaurant.rating

                nsvDescription.show()
                cpiLoading.hide()
            }

        }
    }
}