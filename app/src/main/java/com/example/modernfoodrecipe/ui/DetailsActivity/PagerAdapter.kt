package com.example.modernfoodrecipe.ui.DetailsActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(private val fragments:List<Fragment>,private val fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity){


    override fun getItemCount(): Int {
       return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
      return fragments[position]
    }



}