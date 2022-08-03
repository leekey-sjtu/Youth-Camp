package com.example.hotlist.ui.customize.slidedialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import androidx.core.app.DialogCompat
import com.example.hotlist.R

class SlideDialog(
    var list: List<String> = listOf(),
    val mContext: Context,
    private val isCancelable: Boolean = false,
    private val isBackCancelable: Boolean = true,
    var selectPos: Int = 0
): Dialog(mContext, R.style.SlideDialog) {
    private lateinit var mSelectListener: OnSelectListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_slide_template)
        Log.e("wgw", "onCreate: asdasdasdasd", )
        setCancelable(isCancelable)
        setCanceledOnTouchOutside(isBackCancelable)
        val window = this.window
        window?.setGravity(Gravity.BOTTOM)
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = params

        val tv_cancel = findViewById<TextView>(R.id.tv_cancel)
        val tv_confirm = findViewById<TextView>(R.id.tv_agree)
        val pickerView: EasyPickerView = findViewById(R.id.pickerView)

        tv_cancel.setOnClickListener {
            mSelectListener.onCancel()
            dismiss()
        }

        tv_confirm.setOnClickListener {
            mSelectListener.onAgree(list[selectPos],selectPos)
            dismiss()
        }

        pickerView.setmDataList(list)
        pickerView.setOnScrollChangedListener(object : EasyPickerView.OnScrollChangedListener {

            override fun onScrollChanged(mCurIndex: Int) {
                // 滚动时选中项发生变化
            }

            override fun onScrollFinished(mCurIndex: Int) {
                selectPos = mCurIndex
            }
        })
    }

    fun setOnSelectListener(listener: OnSelectListener) {
        mSelectListener = listener
    }

    interface OnSelectListener {
        fun onCancel()
        fun onAgree(txt: String, pos: Int)
    }
}