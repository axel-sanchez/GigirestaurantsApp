package com.example.gigirestaurantsapp.presentation

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gigirestaurantsapp.R
import com.example.gigirestaurantsapp.core.MyApplication
import com.example.gigirestaurantsapp.data.models.ResponseBody
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.databinding.FragmentNearbyRestaurantsBinding
import com.example.gigirestaurantsapp.domain.usecase.GetNearbyRestaurantsUseCase
import com.example.gigirestaurantsapp.presentation.adapter.RestaurantAdapter
import com.example.gigirestaurantsapp.presentation.viewmodel.RestaurantViewModel
import com.example.gigirestaurantsapp.utils.Constants
import com.example.gigirestaurantsapp.utils.hide
import com.example.gigirestaurantsapp.utils.show
import javax.inject.Inject

class NearbyRestaurantsFragment: Fragment() {

    @Inject lateinit var getNearbyRestaurantsUseCase: GetNearbyRestaurantsUseCase

    private val viewModel: RestaurantViewModel by viewModels(
        factoryProducer = { RestaurantViewModel.RestaurantViewModelFactory(getNearbyRestaurantsUseCase) }
    )

    private var fragmentNearbyRestaurantsBinding: FragmentNearbyRestaurantsBinding? = null
    private val binding get() = fragmentNearbyRestaurantsBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentNearbyRestaurantsBinding = FragmentNearbyRestaurantsBinding.inflate(inflater, container, false)
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
        checkPermission()

        viewModel.getRestaurantsLiveData().observe(viewLifecycleOwner) { response ->
            updateView(response)
        }
    }

    private fun updateView(response: ResponseBody) {
        with(binding) {

            response.restaurants?.let { restaurants ->
                if (restaurants.isEmpty()) {
                    rvProducts.hide()
                    tvErrorText.text = Constants.ApiError.EMPTY_RESTAURANTS.text
                    cvEmptyState.show()
                } else {
                    rvProducts.show()
                    setAdapter(restaurants)
                }
            }?: kotlin.run {
                tvErrorText.text = response.error?.message
                cvEmptyState.show()
                rvProducts.hide()
            }
            cpiLoading.hide()
        }
    }

    private fun setAdapter(restaurants: List<Restaurant?>) {
        val iconFav = resources.getDrawable(R.drawable.ic_fav)
        val iconNoFav = resources.getDrawable(R.drawable.ic_no_fav)
        val restaurantsAdapter = RestaurantAdapter(restaurants, itemClick, iconFav, iconNoFav)
        with(binding.rvProducts) {
            layoutManager = LinearLayoutManager(context)
            adapter = restaurantsAdapter
        }
    }

    private val itemClick = { restaurant: Restaurant? ->
        Toast.makeText(context, restaurant?.name, Toast.LENGTH_SHORT).show()
        /*product?.let {
            val bundle = bundleOf(ID_PRODUCT to it.id)
            val extras = FragmentNavigatorExtras(
                imageView to ID_IMAGE_VIEW
            )
            findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle, null, extras)
        }*/
    }

    private fun getLocation(): String{
        return "-31.418119675147636, -64.49176343201465"
    }

    private fun checkPermission() {
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermission()
        }else{
            viewModel.getRestaurants(getLocation())
        }
    }

    private fun requestPermission() {
        if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
            || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else{
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                viewModel.getRestaurants(getLocation())
            }else{
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
}