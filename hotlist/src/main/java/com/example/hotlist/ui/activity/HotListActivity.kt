package com.example.hotlist.ui.activity

import android.os.Bundle
import com.example.common.base.baseui.BaseActivity
import com.example.hotlist.BR
import com.example.hotlist.R
import com.example.hotlist.databinding.ActivityHotListBinding
import com.example.hotlist.ui.hotlist.HotListTabFragment

class HotListActivity : BaseActivity<ActivityHotListBinding,HotListViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_hot_list
    }

    override fun getVariableId(): Int {
        return BR.hotListViewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.HotListFragmentContainer,HotListTabFragment())
        transaction.commit()
    }
}