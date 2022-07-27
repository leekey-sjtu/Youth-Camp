package com.qxy.bitdance

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.forEach
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qxy.bitdance.databinding.ActivityHomePageBinding

class ActivityHomePage : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflate viewBinding
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.findNavController()
        val navigationView: BottomNavigationView = binding.bottomNav
        NavigationUI.setupWithNavController(navigationView,navController)

        navigationView.menu.forEach {
            val itemView = findViewById<BottomNavigationItemView>(it.itemId)
            // 取消长按会弹出的 ToolTipText
            itemView.setOnLongClickListener { true }

            // 设置点击动画，因为自己设置了点击事件，navigation提供的导航功能就被拦截掉了，所以需要自己进行页面跳转
            itemView.setOnClickListener { item->
                val animatorSet = AnimatorSet()
                animatorSet
                    .play(ObjectAnimator.ofFloat(item,"scaleX",1f,1.1f,0.9f,1f) )
                    .with(ObjectAnimator.ofFloat(item,"scaleY",1f,1.1f,0.9f,1f))
                animatorSet.duration = 500L
                animatorSet.start()

                // 手动进行页面跳转
                when(item.id){
                    R.id.homeFragment -> {
                        navController.navigate(R.id.homeFragment)
                    }
                    R.id.friendFragment -> {
                        navController.navigate(R.id.friendFragment)
                    }
                    R.id.plusFragment -> {
                        navController.navigate(R.id.plusFragment)
                    }
                    R.id.messageFragment -> {
                        navController.navigate(R.id.messageFragment)
                    }
                    R.id.profileFragment -> {
                        navController.navigate(R.id.profileFragment)
                    }
                }

                // 根据 bottomItemView, 手动设置被选中
                it.isChecked = true
            }
        }
    }
}