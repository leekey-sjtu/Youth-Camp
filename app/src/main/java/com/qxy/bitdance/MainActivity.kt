package com.qxy.bitdance

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.qxy.bitdance.baseui.BaseActivity
import com.qxy.bitdance.databinding.ActivityMainBinding
import com.qxy.bitdance.test.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(){
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getVariableId(): Int {
        return BR.mainViewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        getViewModel().showLoading()
        getViewModel().catListData.observe(this) {
            println("MainActivity $it")
        }
        getViewModel().getCatList()
        getViewModel().closeLoading()
    }

}