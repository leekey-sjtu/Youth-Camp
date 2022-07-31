package com.qxy.bitdance.ui.homepage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class VideoFragmentAdapter(fa: FragmentActivity?, private var fragments: List<Fragment>) :
    FragmentStateAdapter(fa!!) {

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

}