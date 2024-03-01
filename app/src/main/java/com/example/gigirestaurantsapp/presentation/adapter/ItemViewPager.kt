package com.example.gigirestaurantsapp.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Clase que funciona como adapter del viewPager de [MainFragment]
 * @author Axel Sanchez
 */
class ViewPageAdapter(fm: FragmentManager, private var items: List<ItemViewPager>) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return items[position].fragment
    }

    override fun getCount(): Int {
        return items.count()
    }

    // This determines the title for each tab
    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        return items[position].title
    }
}

class ItemViewPager(val title : String, val fragment: Fragment)