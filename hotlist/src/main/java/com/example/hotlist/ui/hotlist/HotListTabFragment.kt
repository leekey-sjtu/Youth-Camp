package com.example.hotlist.ui.hotlist

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.common.base.baseui.BaseFragment
import com.example.hotlist.BR
import com.example.hotlist.R
import com.example.hotlist.databinding.FragmentHotListTabBinding
import com.example.hotlist.ui.episode.EpisodeFragment
import com.example.hotlist.ui.films.FilmsListFragment
import com.example.hotlist.ui.variety.VarietyListFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HotListTabFragment : BaseFragment<FragmentHotListTabBinding,HotListTabViewModel>() {
    lateinit var viewBinding: FragmentHotListTabBinding
    private val tabTitle = listOf("电影","剧集","综艺")
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val tabList = listOf(FilmsListFragment(),EpisodeFragment(),VarietyListFragment())

    override fun getLayoutId() = R.layout.fragment_hot_list_tab

    override fun getVariableId() = BR.hotListTabViewModel

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding = getViewDataBinding()
            viewBinding.hotListFragmentViewPager.adapter = HotListFragmentAdapter(tabList, fragmentManager = childFragmentManager, lifecycle = lifecycle)
        TabLayoutMediator(viewBinding.hotListFragmentTabLayout, viewBinding.hotListFragmentViewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        // TODO 将上方的图片与下方的选项结合起来显示
    }
}