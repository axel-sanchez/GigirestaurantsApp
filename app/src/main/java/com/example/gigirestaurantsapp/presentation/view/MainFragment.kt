package com.example.gigirestaurantsapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gigirestaurantsapp.databinding.FragmentMainBinding
import com.example.gigirestaurantsapp.presentation.adapter.ItemViewPager
import com.example.gigirestaurantsapp.presentation.adapter.ViewPageAdapter
import com.example.gigirestaurantsapp.utils.Constants.FAVORITE_RESTAURANTS
import com.example.gigirestaurantsapp.utils.Constants.NEARBY_RESTAURANTS
import java.util.LinkedList

class MainFragment: Fragment() {

    private var fragmentMainBinding: FragmentMainBinding? = null
    private val binding get() = fragmentMainBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentMainBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        val list: MutableList<ItemViewPager> = LinkedList()
        list.add(ItemViewPager(NEARBY_RESTAURANTS, NearbyRestaurantsFragment()))
        list.add(ItemViewPager(FAVORITE_RESTAURANTS, FavoriteRestaurantsFragment()))
        val adapter = ViewPageAdapter(childFragmentManager, list)
        binding.viewpager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewpager)
    }
}