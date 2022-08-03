package com.example.homepage.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

//class HomePageFragmentAdapter(
//    fa: FragmentActivity?,
//    private var fragments: List<Fragment>) :
//    FragmentStateAdapter(fa!!) {
//
//    override fun createFragment(position: Int): Fragment {
//        return fragments[position]
//    }
//
//    override fun getItemCount(): Int {
//        return fragments.size
//    }
//}


class HomePageFragmentAdapter(
    private val fragments: List<Fragment>,
    fragmentManager: FragmentManager?,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager!!, lifecycle) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }
}