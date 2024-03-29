package com.example.gigirestaurantsapp.presentation.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gigirestaurantsapp.R
import com.example.gigirestaurantsapp.core.MyApplication
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.data.models.RestaurantDTO
import com.example.gigirestaurantsapp.databinding.FragmentNearbyRestaurantsBinding
import com.example.gigirestaurantsapp.domain.usecase.DislikeRestaurantUseCase
import com.example.gigirestaurantsapp.domain.usecase.GetNearbyRestaurantsUseCase
import com.example.gigirestaurantsapp.domain.usecase.GetRestaurantsBySearchUseCase
import com.example.gigirestaurantsapp.domain.usecase.LikeRestaurantUseCase
import com.example.gigirestaurantsapp.presentation.adapter.RestaurantAdapter
import com.example.gigirestaurantsapp.presentation.viewmodel.RestaurantViewModel
import com.example.gigirestaurantsapp.utils.Constants
import com.example.gigirestaurantsapp.utils.Constants.LOCATION_ID
import com.example.gigirestaurantsapp.utils.LocationHelper
import com.example.gigirestaurantsapp.utils.hide
import com.example.gigirestaurantsapp.utils.show
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import javax.inject.Inject


class NearbyRestaurantsFragment : Fragment() {

    @Inject
    lateinit var getNearbyRestaurantsUseCase: GetNearbyRestaurantsUseCase
    @Inject
    lateinit var likeRestaurantUseCase: LikeRestaurantUseCase
    @Inject
    lateinit var dislikeRestaurantUseCase: DislikeRestaurantUseCase
    @Inject
    lateinit var getRestaurantsBySearchUseCase: GetRestaurantsBySearchUseCase

    @Inject lateinit var locationHelper: LocationHelper

    private val viewModel: RestaurantViewModel by viewModels(
        factoryProducer = {
            RestaurantViewModel.RestaurantViewModelFactory(
                getNearbyRestaurantsUseCase,
                getRestaurantsBySearchUseCase,
                likeRestaurantUseCase,
                dislikeRestaurantUseCase
            )
        }
    )

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private var fragmentNearbyRestaurantsBinding: FragmentNearbyRestaurantsBinding? = null
    private val binding get() = fragmentNearbyRestaurantsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentNearbyRestaurantsBinding =
            FragmentNearbyRestaurantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentNearbyRestaurantsBinding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        checkPermission()

        viewModel.getRestaurantsBySearchLiveData().observe(viewLifecycleOwner) { response ->
            updateView(response)
        }

        viewModel.getRestaurantsLiveData().observe(viewLifecycleOwner) { response ->
            updateView(response)
        }
    }

    private fun updateView(response: RestaurantDTO) {
        with(binding) {

            response.restaurants?.let { restaurants ->
                if (restaurants.isEmpty()) {
                    rvRestaurants.hide()
                    tvErrorText.text = Constants.ApiError.EMPTY_RESTAURANTS.text
                    cvEmptyState.show()
                } else {
                    rvRestaurants.show()
                    setAdapter(restaurants)
                }
            } ?: kotlin.run {
                tvErrorText.text = response.error?.message
                cvEmptyState.show()
                rvRestaurants.hide()
            }
            cpiLoading.hide()
        }
    }

    private fun setAdapter(restaurants: List<Restaurant?>) {
        val iconLike = ResourcesCompat.getDrawable(resources, R.drawable.ic_like, null)
        val iconDislike = ResourcesCompat.getDrawable(resources, R.drawable.ic_dislike, null)
        val restaurantsAdapter = RestaurantAdapter(
            restaurants,
            itemClick,
            iconLike,
            iconDislike,
            likeRestaurant,
            dislikeRestaurant
        )
        with(binding.rvRestaurants) {
            layoutManager = LinearLayoutManager(context)
            adapter = restaurantsAdapter
        }
    }

    private val likeRestaurant: (restaurant: Restaurant) -> Unit = {
        viewModel.likeRestaurant(it)
    }

    private val dislikeRestaurant: (restaurant: Restaurant) -> Unit = {
        viewModel.dislikeRestaurant(it)
    }

    private val itemClick = { restaurant: Restaurant? ->
        restaurant?.let {
            val bundle = bundleOf(LOCATION_ID to it.locationId)
            findNavController().navigate(
                R.id.action_mainFragment_to_restaurantDetailsFragment,
                bundle,
                null,
                null
            )
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission()
        } else {
            if (locationHelper.isLocationEnabled(requireContext())){
                getRestaurants()
            } else{
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
    }

    private fun requestPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
            || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 1
            )
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 1
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getRestaurants(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val latLong = "${location.latitude}, ${location.longitude}"
                    viewModel.getRestaurants(latLong)
                } else{
                    viewModel.getRestaurants("")
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (locationHelper.isLocationEnabled(requireContext())){
                    getRestaurants()
                } else{
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            } else {
                val dialog = AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.need_granted_permission))
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        requestPermission()
                    }
                    .create()
                dialog.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        requireActivity().menuInflater.inflate(R.menu.menu_action_bar, menu)

        val itSearch = menu.findItem(R.id.itSearch)
        val searchView = itSearch.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getRestaurantsBySearch(query)
                showLoading()
                return false
            }

            override fun onQueryTextChange(p0: String): Boolean {
                return false
            }
        })

        searchView.setOnCloseListener {
            showLoading()
            getRestaurants()
            false
        }
    }

    private fun showLoading(){
        with(binding){
            rvRestaurants.hide()
            cpiLoading.show()
        }
    }
}