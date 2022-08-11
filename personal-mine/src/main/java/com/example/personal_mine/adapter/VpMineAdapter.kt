package com.example.personal_mine.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VpMineAdapter(var fragmentActivity: FragmentActivity, var data:List<Fragment>): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return  data.size
    }

    override fun createFragment(position: Int): Fragment {
        return data[position]
    }
}