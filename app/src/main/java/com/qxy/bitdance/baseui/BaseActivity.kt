package com.qxy.bitdance.baseui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.qxy.bitdance.baseui.dialog.LoadingDialog
import com.qxy.bitdance.utils.transparentStatusBar
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VDB : ViewDataBinding,VM : BaseViewModel> : AppCompatActivity() {

    private var sViewModel: VM? = null
    private var sViewDateBinding: VDB? = null
    private lateinit var loadingDialog : LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        this.transparentStatusBar()
        super.onCreate(savedInstanceState)
        handlerVDB()
        handlerVM()
        receiveLiveData()
        loadingDialog = getLoadingDialog()
        initData(savedInstanceState)
    }

    private fun handlerVDB() {
        sViewDateBinding = DataBindingUtil.setContentView(this, getLayoutId())
        if (sViewDateBinding != null) {
            return
        }else sViewDateBinding!!.lifecycleOwner = this //可以使用liveData对XMl数据更新
    }

    private fun handlerVM() {
        val viewModelClass: Class<BaseViewModel>
        val type = javaClass.genericSuperclass
        viewModelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<BaseViewModel> //获取第1个注解即VM的注解类型
        } else {
            //使用父类的类型
            BaseViewModel::class.java
        }
        sViewModel = ViewModelProvider(this)[viewModelClass] as VM
        if (getVariableId() > 0) {
            if (sViewModel != null){
                lifecycle.addObserver(sViewModel!!)
            }
            sViewDateBinding?.setVariable(getVariableId(), sViewModel)
        }
    }

    abstract fun getLayoutId(): Int

    private fun receiveLiveData() {
        sViewModel?.loadingEvent?.observe(this) { aBoolean ->
            if (aBoolean) {
                showLoading()
            } else {
                dismissLoading()
            }
        }
    }

    open fun showLoading() {
        if (!loadingDialog.isShowing) {
            loadingDialog.show()
        }
    }

    private fun getLoadingDialog(): LoadingDialog = LoadingDialog(this)


    open fun dismissLoading() {
        loadingDialog.dismiss()
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun getVariableId(): Int

    /**
     * 初始化数据，相当于OnCreate方法
     */
    abstract fun initData(savedInstanceState: Bundle?)

    /**
     * 获取当前Activity的ViewModel
     */
    open fun getViewModel(): VM = sViewModel!!

}