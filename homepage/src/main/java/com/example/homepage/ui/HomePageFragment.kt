package com.example.homepage.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.common.base.baseui.BaseFragment
import com.example.homepage.BR
import com.example.homepage.R
import com.example.homepage.adapter.HomePageAdapter
import com.example.homepage.databinding.FragmentHomePageBinding
import com.example.homepage.viewmodel.HomePageViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomePageFragment : BaseFragment<FragmentHomePageBinding, HomePageViewModel>() {

    companion object {
        const val END_SWIPE_REFRESH_FOR_SUCCESS = 1000
        const val END_SWIPE_REFRESH_FOR_FAIL = 1001
    }

    private lateinit var tabLayout: TabLayout // 顶部导航栏
    private lateinit var viewPager: ViewPager2
    private lateinit var imgAdd: ImageView
    private lateinit var imgSearch: ImageView
    private val videoFollowFragment = NewsFragment()
    private val videoRecommendFragment = VideoFragment()
    
    override fun getLayoutId() = R.layout.fragment_home_page

    override fun getVariableId() = BR.homePageViewModel

    override fun initData(savedInstanceState: Bundle?) {
        // 设置 ViewDataBinding
        setViewDataBinding()

        // 设置 ViewPager
        setViewPager()

        // 设置 TabLayout
        setTabLayout()

        // 初始化
        init()
    }

    private fun setViewDataBinding() {
        tabLayout = getViewDataBinding().tabLayout
        viewPager = getViewDataBinding().viewPager
        imgAdd = getViewDataBinding().imgAdd
        imgSearch = getViewDataBinding().imgSearch
    }

    private fun setViewPager() {
        val fragments = mutableListOf(videoFollowFragment, videoRecommendFragment)
        viewPager.adapter = HomePageAdapter(fragments, fragmentManager, lifecycle) // 绑定数据
        viewPager.offscreenPageLimit = fragments.size  // 设置viewPager的预加载数量
    }
    
    private fun setTabLayout() {
        TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->  //绑定TabLayout与viewPager2Video
            tab.customView = layoutInflater.inflate(R.layout.fragment_video_tab_view, null)
            val textView = tab.view.findViewById<TextView>(R.id.textView)
            when (position) {
                0 -> { textView.text = "头条" }
                1 -> { textView.text = "视频" }
            }
        }.attach()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val textView = tab!!.customView!!.findViewById<TextView>(R.id.textView)
                if (tab.position == 0) {
                    textView.setTextColor(Color.BLACK)
                    tabLayout.setSelectedTabIndicatorColor(Color.BLACK)
                    imgAdd.setColorFilter(Color.BLACK)
                    imgSearch.setColorFilter(Color.BLACK)
                } else {
                    textView.setTextColor(Color.WHITE)
                    tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
                    imgAdd.setColorFilter(Color.WHITE)
                    imgSearch.setColorFilter(Color.WHITE)
                }
                val animator1 = ObjectAnimator.ofFloat(textView, "scaleX", 1f, 1.1f)
                val animator2 = ObjectAnimator.ofFloat(textView, "scaleY", 1f, 1.1f)
                val animSet = AnimatorSet()
                animSet.duration = 100
                animSet.play(animator1).with(animator2)
                animSet.start()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val textView = tab!!.customView!!.findViewById<TextView>(R.id.textView)
                textView.setTextColor(Color.parseColor("#949494"))
                val animator1 = ObjectAnimator.ofFloat(textView, "scaleX", 1.1f, 1f)
                val animator2 = ObjectAnimator.ofFloat(textView, "scaleY", 1.1f, 1f)
                val animSet = AnimatorSet()
                animSet.duration = 100
                animSet.play(animator1).with(animator2)
                animSet.start()
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
    
    private fun init() {
        viewPager.currentItem = 1
        val textView = tabLayout.getTabAt(1)!!.customView!!.findViewById<TextView>(R.id.textView)
        textView.setTextColor(Color.WHITE)
        textView.scaleX = 1.1f
        textView.scaleY = 1.1f
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        val recyclerView= videoRecommendFragment.viewPager.getChildAt(0) as RecyclerView
        val view = recyclerView.layoutManager?.findViewByPosition(videoRecommendFragment.viewPager.currentItem)
        val videoView = view?.findViewById<VideoView>(R.id.videoView)
        if (hidden) videoView?.pause() else videoView?.start()
    }

}