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
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.homepage.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomePageFragment : Fragment() {

    private val tabLayout: TabLayout by lazy { requireView().findViewById(R.id.tabLayout) }  //顶部导航栏
    private val viewPager: ViewPager2 by lazy { requireView().findViewById(R.id.viewPager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = mutableListOf(
            VideoFollowFragment(),
            VideoRecommendFragment(),
        )
        viewPager.adapter = VideoFragmentAdapter(activity, fragments)  // 绑定数据

        viewPager.currentItem = 1

        TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->  //绑定TabLayout与viewPager2Video
            tab.customView = layoutInflater.inflate(R.layout.fragment_video_tab_view, null)
            val textView = tab.view.findViewById<TextView>(R.id.textView)
            when (position) {
                0 -> { textView.text = "关注" }
                1 -> { textView.text = "推荐" }
            }
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("wdw", "${tab!!.position} onTabSelected")
                val textView = tab.customView!!.findViewById<TextView>(R.id.textView)
                if (tab.position == 1) textView.setTextColor(Color.WHITE) else textView.setTextColor(Color.BLACK)
                val animator1 = ObjectAnimator.ofFloat(textView, "scaleX", 1f, 1.3f)
                val animator2 = ObjectAnimator.ofFloat(textView, "scaleY", 1f, 1.3f)
                val animSet = AnimatorSet()
                animSet.duration = 100
                animSet.play(animator1).with(animator2)
                animSet.start()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("wdw", "${tab!!.position} onTabUnselected")
                val textView = tab.customView!!.findViewById<TextView>(R.id.textView)
                textView.setTextColor(Color.parseColor("#949494"))
                val animator1 = ObjectAnimator.ofFloat(textView, "scaleX", 1.3f, 1f)
                val animator2 = ObjectAnimator.ofFloat(textView, "scaleY", 1.3f, 1f)
                val animSet = AnimatorSet()
                animSet.duration = 100
                animSet.play(animator1).with(animator2)
                animSet.start()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    companion object {

    }
}