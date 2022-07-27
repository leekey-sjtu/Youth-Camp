package com.qxy.bitdance.baseui.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qxy.bitdance.R

abstract class BaseDialog<VDB : ViewDataBinding> : AlertDialog {
    private var activity: Context
    var viewDataBinding: VDB? = null
        private set

    constructor(context: Context) : super(context, R.style.half_trans_Dialog) {
        activity = context
    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        activity = context
    }

    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener) {
        activity = context
    }

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        val decorView = window!!.decorView
        val contentView = decorView.findViewById<ViewGroup>(android.R.id.content)
        viewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            layoutId, contentView, true
        )
        initData()
        if (isFullScreen) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window!!.insetsController!!.hide(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE)
            } else {
                window!!.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                window!!.decorView.setOnSystemUiVisibilityChangeListener {
                    var uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or  //布局位于状态栏下方
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or  //全屏
                            View.SYSTEM_UI_FLAG_FULLSCREEN or  //隐藏导航栏
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    uiOptions = uiOptions or 0x00001000
                    window!!.decorView.systemUiVisibility = uiOptions
                }
            }
        }
    }

    private fun fullScreenImmersive(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
            view.systemUiVisibility = uiOptions
        }
    }

    abstract val layoutId: Int

    abstract fun initData()
    fun getActivity(): AppCompatActivity {
        return activity as AppCompatActivity
    }

    override fun show() {
        if (isFullScreen) {
            this.window!!.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            )
        }
        try {
            if (activity is Activity) {
                val activity = activity as Activity
                if (activity.isDestroyed || activity.isFinishing || isShowing) {
                    return
                }
                super.show()
            }
        } catch (e: Exception) {
        }
        if (isFullScreen) {
            fullScreenImmersive(window!!.decorView)
            this.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        }
    }

    override fun dismiss() {
        try {
            if (activity is Activity) {
                val activity = activity as Activity
                if (activity.isFinishing || !isShowing) {
                    return
                }
                super.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * dialog 是否要全屏
     * 全屏取决于所在的Activity是否是全屏的
     */
    private val isFullScreen: Boolean get() = true

    fun <T : ViewModel> getVm(modelClass: Class<T>): T {
        return ViewModelProvider(getActivity())[modelClass]
    }
}