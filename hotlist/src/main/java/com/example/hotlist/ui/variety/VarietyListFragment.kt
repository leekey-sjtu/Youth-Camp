package com.example.hotlist.ui.variety

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseFragment
import com.example.hotlist.BR
import com.example.hotlist.R
import com.example.hotlist.databinding.FragmentVarietyListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class VarietyListFragment : BaseFragment<FragmentVarietyListBinding,VarietyListViewModel>() {
    private lateinit var viewModel: VarietyListViewModel
    private lateinit var videBinding: FragmentVarietyListBinding

    override fun getLayoutId() = R.layout.fragment_variety_list

    override fun getVariableId() = BR.varietyListViewModel

    override fun initData(savedInstanceState: Bundle?) {
        viewModel = getViewModel()
        videBinding = getViewDataBinding()

        viewModel.viewModelScope.launch {
            viewModel.updateNextPageVarietiesList()
            viewModel.varietiesFlow.collect{
                videBinding.varietyList.adapter = VarietyListAdapter(it)
            }
        }
    }

}