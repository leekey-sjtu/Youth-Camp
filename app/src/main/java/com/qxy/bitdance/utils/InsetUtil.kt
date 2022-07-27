package com.qxy.bitdance.utils

import android.app.Activity
import android.view.WindowInsets
import androidx.core.view.ViewCompat

/**
 * 隐藏输入法
 */
fun Activity.hideIme() {
    val controller = ViewCompat.getWindowInsetsController(window.decorView)
    controller?.hide(WindowInsets.Type.ime())
}

/**
 * 显示输入法
 */
fun Activity.showIme() {
    val controller = ViewCompat.getWindowInsetsController(window.decorView)
    controller?.show(WindowInsets.Type.ime())
}
