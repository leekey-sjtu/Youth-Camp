package com.qxy.bitdance.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.WindowInsets.Type.statusBars
import androidx.core.view.ViewCompat

/**
 * 设置透明状态栏
 */
fun Activity.setStatusBarColor(color: Int) {
//    val controller = ViewCompat.getWindowInsetsController(window.decorView)
//    // 隐藏状态栏
//    controller?.hide(statusBars())
    // 设置状态栏颜色为透明
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


