package com.example.hotlist.ui.episode

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseFragment
import com.example.hotlist.BR
import com.example.hotlist.R
import com.example.hotlist.databinding.FragmentEpisodeListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class EpisodeFragment : BaseFragment<FragmentEpisodeListBinding,EpisodeViewModel>() {
    private lateinit var viewModel: EpisodeViewModel
    private lateinit var viewBinding: FragmentEpisodeListBinding

    override fun getLayoutId() = R.layout.fragment_episode_list

    override fun getVariableId() = BR.episodeViewModel

    override fun initData(savedInstanceState: Bundle?) {
        viewModel = getViewModel()
        viewBinding = getViewDataBinding()

        viewModel.viewModelScope.launch {
            viewModel.updateNextPageEpisodesList()
            viewModel.episodesFlow.collect{
                viewBinding.episodesList.adapter = EpisodeListAdapter(it)
            }
        }
    }
}