package com.example.personal.ui.friendList.fragment.fan

import android.os.Bundle
import com.example.common.base.baseui.BaseFragment
import com.example.personal.BR
import com.example.personal.R
import com.example.personal.databinding.FragmentFanBinding


class FanFragment : BaseFragment<FragmentFanBinding,FanViewModel>() {
    override fun getLayoutId() = R.layout.fragment_fan

    override fun getVariableId() = BR.fanViewModel

    override fun initData(savedInstanceState: Bundle?) {

    }

}