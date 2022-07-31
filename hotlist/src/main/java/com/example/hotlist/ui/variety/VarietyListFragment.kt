package com.example.hotlist.ui.variety

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.common.base.baseui.BaseFragment
import com.example.hotlist.BR
import com.example.hotlist.R
import com.example.hotlist.databinding.FragmentVarietyListBinding


class VarietyListFragment : BaseFragment<FragmentVarietyListBinding,VarietyListViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_variety_list
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun getVariableId(): Int {
        return BR.varietyListViewModel
    }
}