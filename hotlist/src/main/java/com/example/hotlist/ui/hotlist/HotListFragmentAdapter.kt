package com.example.hotlist.ui.hotlist

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HotListFragmentAdapter(private val tabList: List<Fragment>, fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = tabList.size

    override fun createFragment(position: Int) = tabList[position]
}