package com.example.homepage

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.homepage.ui.homepage.HomePageFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private val fragmentContainer: TabLayout by lazy { findViewById(R.id.fragment_container) }
    private val tabLayout: TabLayout by lazy { findViewById(R.id.tabLayout) }

    private val homePageFragment =  HomePageFragment()
    private val testFragment =  TestFragment()
    private lateinit var currentFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化首个fragment
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, homePageFragment).commit()

        // 记录当前fragment为homePageFragment
        currentFragment = homePageFragment

        // 设置底部导航栏View
        setBottomNavigationTabCustomView()

        // 设置底部导航栏tabLayout点击事件
        setBottomNavigationTabSelect()

    }


    private fun setBottomNavigationTabCustomView() {
        tabLayout.getTabAt(0)?.customView = layoutInflater.inflate(R.layout.bottom_navigation_tab, null)
        tabLayout.getTabAt(0)?.customView?.findViewById<TextView>(R.id.textView)?.text = "首页"
        tabLayout.getTabAt(1)?.customView = layoutInflater.inflate(R.layout.bottom_navigation_tab, null)
        tabLayout.getTabAt(1)?.customView?.findViewById<TextView>(R.id.textView)?.text = "朋友"
        tabLayout.getTabAt(2)?.customView = layoutInflater.inflate(R.layout.bottom_navigation_tab_middle, null)
        tabLayout.getTabAt(2)?.customView?.findViewById<ImageView>(R.id.imgView)?.setImageResource(R.drawable.ic_bottom_navigation_tab)
        tabLayout.getTabAt(3)?.customView = layoutInflater.inflate(R.layout.bottom_navigation_tab, null)
        tabLayout.getTabAt(3)?.customView?.findViewById<TextView>(R.id.textView)?.text = "消息"
        tabLayout.getTabAt(4)?.customView = layoutInflater.inflate(R.layout.bottom_navigation_tab, null)
        tabLayout.getTabAt(4)?.customView?.findViewById<TextView>(R.id.textView)?.text = "我"
    }


    private fun setBottomNavigationTabSelect() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> switchFragment(homePageFragment)
                    1 -> switchFragment(testFragment)
                    2 -> switchFragment(testFragment)
                    3 -> switchFragment(testFragment)
                    4 -> switchFragment(testFragment)
                }
                if (tab.position != 2) {
                    val textView = tab.customView!!.findViewById<TextView>(R.id.textView)
                    textView.setTextColor(Color.BLACK)
                    val animator1 = ObjectAnimator.ofFloat(textView, "scaleX", 1f, 1.2f, 1f)
                    val animator2 = ObjectAnimator.ofFloat(textView, "scaleY", 1f, 1.2f, 1f)
                    val animSet = AnimatorSet()
                    animSet.duration = 200
                    animSet.play(animator1).with(animator2)
                    animSet.start()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab!!.position != 2) {
                    val textView = tab.customView!!.findViewById<TextView>(R.id.textView)
                    textView.setTextColor(Color.GRAY)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }


    // 切换fragment
    private fun switchFragment(newFragment: Fragment) {
        if (newFragment.isAdded) {                          // 判断fragment是否已添加
            supportFragmentManager.beginTransaction()
                .hide(currentFragment)                      // hide当前fragment
                .show(newFragment)                          // 显示新的fragment
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .hide(currentFragment)                      // hide当前fragment
                .add(R.id.fragment_container, newFragment)  // 先add，再显示新的fragment
                .commit()
        }
        currentFragment = newFragment                       //记录currentFragment
    }

}