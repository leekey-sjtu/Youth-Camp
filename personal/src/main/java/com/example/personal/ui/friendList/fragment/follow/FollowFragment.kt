package com.example.personal.ui.friendList.fragment.follow

import android.os.Bundle
import com.example.common.base.baseui.BaseFragment
import com.example.personal.BR
import com.example.personal.R
import com.example.personal.databinding.FragmentFanBinding


class FollowFragment : BaseFragment<FragmentFanBinding,FollowViewModel>() {
    override fun getLayoutId() = R.layout.fragment_follow

    override fun getVariableId() = BR.followViewModel

    override fun initData(savedInstanceState: Bundle?) {

    }

}