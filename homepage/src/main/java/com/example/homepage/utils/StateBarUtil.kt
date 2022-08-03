package com.example.homepage.utils

import android.app.Activity
import android.content.Context
import androidx.core.view.ViewCompat

/**
 * 设置透明状态栏
 */
fun Activity.setStatusBarColor(color: Int) {
    // 设置状态栏颜色
    window.statusBarColor = color
}

/**
 * 状态栏反色
 */
fun Activity.setAndroidNativeLightStatusBar() {
    val controller = ViewCompat.getWindowInsetsController(window.decorView)
    controller?.isAppearanceLightStatusBars = !isDarkMode()
}

/**
 * 获取当前是否为深色模式
 * 深色模式的值为:0x21
 * 浅色模式的值为:0x11
 * @return true 为是深色模式   false为不是深色模式
 */
fun Context.isDarkMode(): Boolean {
    return resources.configuration.uiMode == 0x21
}