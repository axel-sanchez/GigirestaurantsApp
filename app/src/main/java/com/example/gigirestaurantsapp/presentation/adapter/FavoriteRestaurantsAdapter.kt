package com.example.gigirestaurantsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.databinding.ItemFavedRestaurantBinding

/**
 * @author Axel Sanchez
 */
class FavoriteRestaurantsAdapter(
    private var mItems: MutableList<Restaurant?>,
    private val itemClick: (Restaurant?) -> Unit?
) : RecyclerView.Adapter<FavoriteRestaurantsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemFavedRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Restaurant?,
            itemClick: (Restaurant?) -> Unit?,
            position: Int
        ) {

            with(binding) {
                item?.let { restaurant ->
                    itemView.setOnClickListener {
                        itemClick(restaurant)
                    }

                    tvName.text = restaurant.name
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerRowBinding: ItemFavedRestaurantBinding =
            ItemFavedRestaurantBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(mItems[position], itemClick, position)


    override fun getItemCount() = mItems.size
}