package com.example.homepage.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.homepage.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomePageFragment : Fragment() {

    private val tabLayout: TabLayout by lazy { requireView().findViewById(R.id.tabLayout) }  //顶部导航栏
    private val viewPager: ViewPager2 by lazy { requireView().findViewById(R.id.viewPager) }
    private val imgAdd: ImageView by lazy { requireView().findViewById(R.id.imgAdd) }
    private val imgSearch: ImageView by lazy { requireView().findViewById(R.id.imgSearch) }
    private val videoFollowFragment = VideoFollowFragment()
    private val videoRecommendFragment = VideoRecommendFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = mutableListOf(
            videoFollowFragment,
            videoRecommendFragment
        )
        viewPager.adapter = HomePageFragmentAdapter(fragments, fragmentManager, lifecycle) // 绑定数据
        viewPager.offscreenPageLimit = fragments.size  // 设置viewPager的预加载数量

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

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        // 初始化
//        viewPager.currentItem = 1
//        val textView = tabLayout.getTabAt(1)!!.customView!!.findViewById<TextView>(R.id.textView)
//        textView.setTextColor(Color.WHITE)
//        textView.scaleX = 1.1f
//        textView.scaleY = 1.1f
//        tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        val recyclerView= videoRecommendFragment.viewPager.getChildAt(0) as RecyclerView
        val view = recyclerView.layoutManager?.findViewByPosition(videoRecommendFragment.viewPager.currentItem)
        val videoView = view?.findViewById<VideoView>(R.id.videoView)
        if (hidden) {
            Log.d("wdw", "homepage hidden")
            videoView?.pause()
        } else {
            Log.d("wdw", "homepage not hidden")
            videoView?.start()
        }
    }

}