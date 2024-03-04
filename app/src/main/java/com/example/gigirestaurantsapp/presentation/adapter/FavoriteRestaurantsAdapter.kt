package com.example.gigirestaurantsapp.presentation.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gigirestaurantsapp.data.models.Restaurant
import com.example.gigirestaurantsapp.databinding.ItemRestaurantBinding

/**
 * @author Axel Sanchez
 */
class FavoriteRestaurantsAdapter(
    private var mItems: MutableList<Restaurant?>,
    private val iconFav: Drawable,
    private val unFavRestaurant: (restaurant: Restaurant) -> Unit,
    private val itemClick: (Restaurant?) -> Unit?
) : RecyclerView.Adapter<FavoriteRestaurantsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Restaurant?,
            itemClick: (Restaurant?) -> Unit?,
            position: Int,
            deleteItem: (Restaurant) -> Unit
        ) {

            with(binding) {
                item?.let { restaurant ->
                    itemView.setOnClickListener {
                        itemClick(restaurant)
                    }

                    ivFav.setImageDrawable(iconFav)

                    ivFav.setOnClickListener {
                        unFavRestaurant(restaurant)
                        deleteItem(restaurant)
                    }
                    tvName.text = restaurant.name
                }
            }
        }
    }

    private val deleteItem = { restaurant: Restaurant ->
        val position = mItems.indexOf(mItems.find { x -> x?.locationId == restaurant.locationId })
        mItems.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerRowBinding: ItemRestaurantBinding =
            ItemRestaurantBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(mItems[position], itemClick, position, deleteItem)


    override fun getItemCount() = mItems.size
}