package com.example.hotlist.ui.variety

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseFragment
import com.example.hotlist.BR
import com.example.hotlist.R
import com.example.hotlist.databinding.FragmentVarietyListBinding
import com.example.hotlist.ui.customize.slidedialog.SlideDialog
import com.example.hotlist.ui.films.FilmsListAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class VarietyListFragment : BaseFragment<FragmentVarietyListBinding,VarietyListViewModel>() {
    private lateinit var viewModel: VarietyListViewModel
    private lateinit var viewBinding: FragmentVarietyListBinding

    override fun getLayoutId() = R.layout.fragment_variety_list

    override fun getVariableId() = BR.varietyListViewModel

    override fun initData(savedInstanceState: Bundle?) {
        viewModel = getViewModel()
        viewBinding = getViewDataBinding()

        val slideDialog = SlideDialog(mContext = requireContext(), isCancelable = true, isBackCancelable = true)

        viewModel.getVarietiesVersionList(0L,20L)
        lifecycleScope.launchWhenCreated {
            viewModel.varietiesVersionListStateFlow.collect{
                slideDialog.list = it
                viewBinding.textTitle.text = if (it.isNotEmpty()) it[0] else ""
            }
        }

        viewModel.getVarietiesList(140)
        viewModel.viewModelScope.launch {
            viewModel.varietiesListStateFlow.collect{
                viewBinding.varietyList.adapter = FilmsListAdapter(it)
            }
        }

        viewBinding.textTitle.setOnClickListener {
            slideDialog.show()
        }

        slideDialog.setOnSelectListener(object : SlideDialog.OnSelectListener{
            override fun onCancel() {}

            override fun onAgree(txt: String, pos: Int) {
                Log.e("wgw", "onAgree: $pos $txt", )
                viewModel.getVarietiesList(version = viewModel.varietiesVersionList[pos])
            }
        })
    }

}