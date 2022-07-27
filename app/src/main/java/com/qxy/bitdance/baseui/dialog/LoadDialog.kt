package com.qxy.bitdance.baseui.dialog

import android.content.Context
import com.qxy.bitdance.R
import com.qxy.bitdance.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) : BaseDialog<DialogLoadingBinding>(context, R.style.trans_Dialog) {
    override val layoutId: Int
        get() = R.layout.dialog_loading

    override fun initData() {}
}