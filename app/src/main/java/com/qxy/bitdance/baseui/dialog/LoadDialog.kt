package com.qxy.bitdance.baseui.dialog

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import com.qxy.bitdance.R

class LoadingDialog(context: Context, themeId: Int) : Dialog(context, themeId) {

    init {
        initView()
    }

    private fun initView(){
        setContentView(R.layout.dialog_loading)
        setCanceledOnTouchOutside(true)
        val attributes: WindowManager.LayoutParams = window!!.attributes
        attributes.alpha = 0.5f
        window!!.attributes = attributes
        setCancelable(false)
    }

}