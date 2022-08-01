package com.example.hotlist.ui.films

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.baseui.BaseFragment
import com.example.common.base.service.TokenProService
import com.example.hotlist.BR
import com.example.hotlist.R
import com.example.hotlist.databinding.FragmentFilmsListBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FilmsListFragment : BaseFragment<FragmentFilmsListBinding,FilmsListViewModel>() {
    private lateinit var viewBinding: FragmentFilmsListBinding
    private lateinit var viewModel: FilmsListViewModel

    override fun getLayoutId() = R.layout.fragment_films_list

    override fun getVariableId() = BR.filmsListViewModel

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding = getViewDataBinding()
        viewModel = getViewModel()

        viewModel.viewModelScope.launch {
            viewModel.updateNextPageFilmsList()
            viewModel.filmsFlow.collect{
                viewBinding.filmsList.adapter = FilmsListAdapter(it)
            }
        }
    }
}