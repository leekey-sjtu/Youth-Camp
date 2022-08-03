package com.example.hotlist.ui.hotlist

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.common.base.baseui.BaseFragment
import com.example.common.base.constants.TokenConstants
import com.example.hotlist.BR
import com.example.hotlist.R
import com.example.hotlist.databinding.FragmentHotListTabBinding
import com.example.hotlist.ui.episode.EpisodeFragment
import com.example.hotlist.ui.films.FilmsListFragment
import com.example.hotlist.ui.variety.VarietyListFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HotListTabFragment : BaseFragment<FragmentHotListTabBinding,HotListTabViewModel>() {
    lateinit var viewBinding: FragmentHotListTabBinding
    lateinit var viewModel: HotListTabViewModel
    private val tabTitle = listOf("电影","剧集","综艺")
    private val tabList = listOf(FilmsListFragment(),EpisodeFragment(),VarietyListFragment())
    private val topImagesList = listOf(R.drawable.film,R.drawable.episode,R.drawable.variety)

    override fun getLayoutId() = R.layout.fragment_hot_list_tab

    override fun getVariableId() = BR.hotListTabViewModel

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding = getViewDataBinding()
        viewModel = getViewModel()

        // 将 viewpage 与 fragment 绑定起来
        viewBinding.hotListFragmentViewPager.adapter = HotListFragmentAdapter(tabList, fragmentManager = childFragmentManager, lifecycle = lifecycle)
        TabLayoutMediator(viewBinding.hotListFragmentTabLayout, viewBinding.hotListFragmentViewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        // 将上方的图片与下方的选项结合起来显示
        viewBinding.hotListFragmentViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("wgw", "onPageSelected: $position is selected", )
                viewBinding.imageTop.setImageResource(topImagesList[position])
            }
        })
    }
}