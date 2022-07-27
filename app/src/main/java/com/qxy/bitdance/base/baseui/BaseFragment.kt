package com.qxy.bitdance.base.baseui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.qxy.bitdance.R
import com.qxy.bitdance.base.baseui.dialog.LoadingDialog
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VDB : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    private var viewModel: VM? = null
    private var viewDataBinding: VDB? = null
    private lateinit var loadingDialog : LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate<VDB>(inflater, getLayoutId(), container, false)
        return if (viewDataBinding != null) {
            viewDataBinding!!.lifecycleOwner = this
            viewDataBinding!!.root
        } else inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handlerVM()
        loadingDialog = LoadingDialog(activity!!,R.style.trans_Dialog)
        receiveLiveData()
        initData(savedInstanceState)
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
        viewModel = ViewModelProvider(requireActivity())[viewModelClass] as VM //找到Activity对于的VM
        if (viewModel == null) {
            viewModel = ViewModelProvider(this)[viewModelClass] as VM //fragment自己的VM 不是Activity
        }
        if (getVariableId() > 0) {
            if (viewModel != null) lifecycle.addObserver(viewModel!!)
            viewDataBinding?.setVariable(getVariableId(), viewModel)
        }
    }

    private fun receiveLiveData() {
        if (viewModel != null){
            viewModel!!.loadingEvent.observe(viewLifecycleOwner){ aBoolean ->
                if (aBoolean) {
                    showLoading()
                } else {
                    dismissLoading()
                }
            }
        }
    }

    abstract fun getLayoutId(): Int

    open fun getViewModel(): VM {
        return viewModel!!
    }

    open fun getViewDataBinding(): VDB {
        return viewDataBinding!!
    }

    abstract fun initData(savedInstanceState: Bundle?)

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun getVariableId(): Int

    open fun showLoading() {
        if (!loadingDialog.isShowing) {
            loadingDialog.show()
        }
    }

    open fun dismissLoading() {
        loadingDialog.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

}