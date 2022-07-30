package com.example.hotlist.ui.hotlist

import android.os.Bundle
import com.example.common.base.baseui.BaseFragment
import com.example.hotlist.BR
import com.example.hotlist.R
import com.example.hotlist.databinding.FragmentHotListTabBinding


class HotListTabFragment : BaseFragment<FragmentHotListTabBinding,HotListTabViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_hot_list_tab
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun getVariableId(): Int {
        return BR.hotListTabViewModel
    }

}