package com.example.personal.ui.friendList

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.common.base.baseui.BaseActivity
import com.example.common.base.utils.setAndroidNativeLightStatusBar
import com.example.common.base.utils.setStatusBarColor
import com.example.personal.BR
import com.example.personal.R
import com.example.personal.databinding.ActivityFriendBinding
import com.example.personal.ui.friendList.fragment.fan.FanFragment
import com.example.personal.ui.friendList.fragment.follow.FollowFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FriendActivity : BaseActivity<ActivityFriendBinding,FriendViewModel>() {

    private val title = listOf("关注","粉丝")
    private lateinit var friendVP : ViewPager2
    private lateinit var friendTab : TabLayout

    override fun getLayoutId() = R.layout.activity_friend

    override fun getVariableId() = BR.friendViewModel

    override fun initData(savedInstanceState: Bundle?) {
        friendTab = getDataBinding().friendTab
        friendVP = getDataBinding().friendVP
        val tabList = mutableListOf<Fragment>(FollowFragment(),FanFragment())
        getDataBinding().friendBack.setOnClickListener { finish() }

        friendVP.adapter = FriendFragmentAdapter(tabList,supportFragmentManager,lifecycle)
        val tabLayoutMediator = TabLayoutMediator(friendTab,friendVP) { tab, position ->
            tab.text = title[position]
        }
        tabLayoutMediator.attach()

        setAndroidNativeLightStatusBar()
        setStatusBarColor(Color.WHITE)
        if (intent.getIntExtra("fans_or_follow",1) == 1) friendVP.currentItem = 0
        if (intent.getIntExtra("fans_or_follow",1) == 2) friendVP.currentItem = 1
    }


}