package com.example.hotlist.ui.films

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.common.base.baseui.BaseFragment
import com.example.hotlist.BR
import com.example.hotlist.R
import com.example.hotlist.databinding.FragmentFilmsListBinding


class FilmsListFragment : BaseFragment<FragmentFilmsListBinding,FilmsListViewModel>() {
    private lateinit var viewBinding: FragmentFilmsListBinding
    private lateinit var viewModel: FilmsListViewModel

    override fun getLayoutId() = R.layout.fragment_films_list

    override fun getVariableId() = BR.filmsListViewModel

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding = getViewDataBinding()
        viewModel = getViewModel()
        viewModel.getHotListVersion(1,10,1)
        viewBinding.filmsList.adapter = FilmsListAdapter(viewModel.filmsList)
    }

}