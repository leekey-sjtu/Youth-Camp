package com.example.hotlist.ui.films

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseFragment
import com.example.hotlist.BR
import com.example.hotlist.R
import com.example.hotlist.databinding.FragmentFilmsListBinding
import com.example.hotlist.ui.customize.slidedialog.SlideDialog
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class FilmsListFragment : BaseFragment<FragmentFilmsListBinding,FilmsListViewModel>() {
    private lateinit var viewBinding: FragmentFilmsListBinding
    private lateinit var viewModel: FilmsListViewModel

    override fun getLayoutId() = R.layout.fragment_films_list

    override fun getVariableId() = BR.filmsListViewModel

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding = getViewDataBinding()
        viewModel = getViewModel()

        val slideDialog = SlideDialog(mContext = requireContext(), isCancelable = true, isBackCancelable = true)

        viewModel.getFilmsVersionList(0L,20L)
        viewModel.viewModelScope.launch {
            viewModel.filmsVersionListStateFlow.collect{
                slideDialog.list = it
                viewBinding.textTitle.text = if (it.isNotEmpty()) it[0] else "本周榜"
            }
        }

        viewModel.getFilmsList(140)
        viewModel.viewModelScope.launch {
            viewModel.filmsListStateFlow.collect{
                viewBinding.filmsList.adapter = FilmsListAdapter(it)
                viewBinding.filmsList.adapter?.notifyItemRangeChanged(0,it.size)
            }
        }

        viewBinding.textTitle.setOnClickListener {
            slideDialog.show()
        }

        slideDialog.setOnSelectListener(object : SlideDialog.OnSelectListener{
            override fun onCancel() {}

            override fun onAgree(txt: String, pos: Int) {
                viewModel.getFilmsList(version = viewModel.filmsVersionList[pos])
                viewBinding.textTitle.text = txt
            }
        })
    }
}