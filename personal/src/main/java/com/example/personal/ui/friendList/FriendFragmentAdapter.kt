package com.example.personal.ui.friendList

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FriendFragmentAdapter(tabList : MutableList<Fragment>,fragmentManager: FragmentManager,lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager,lifecycle) {

    private var tabList : MutableList<Fragment>

    init {
        this.tabList = tabList
    }

    override fun getItemCount() = tabList.size

    override fun createFragment(position: Int) = tabList[position]
}