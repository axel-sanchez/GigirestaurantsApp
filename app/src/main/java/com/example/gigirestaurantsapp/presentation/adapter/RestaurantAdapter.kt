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
class RestaurantAdapter(
    private var mItems: List<Restaurant?>,
    private val itemClick: (Restaurant?) -> Unit?,
    private val iconFav: Drawable,
    private val iconNoFav: Drawable
) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Restaurant?, itemClick: (Restaurant?) -> Unit?, position: Int) {

            with(binding) {
                item?.let { restaurant ->
                    itemView.setOnClickListener {
                        itemClick(restaurant)
                    }

                    ivFav.setOnClickListener{
                        if (ivFav.drawable != iconFav){
                            ivFav.setImageDrawable(iconFav)
                        } else {
                            ivFav.setImageDrawable(iconNoFav)
                        }
                    }
                    tvName.text = restaurant.name
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerRowBinding: ItemRestaurantBinding =
            ItemRestaurantBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(mItems[position], itemClick, position)


    override fun getItemCount() = mItems.size
}