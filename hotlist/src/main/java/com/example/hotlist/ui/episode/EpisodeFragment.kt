package com.example.hotlist.ui.episode

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseFragment
import com.example.hotlist.BR
import com.example.hotlist.R
import com.example.hotlist.databinding.FragmentEpisodeListBinding
import com.example.hotlist.ui.customize.slidedialog.SlideDialog
import com.example.hotlist.ui.films.FilmsListAdapter
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

        val slideDialog = SlideDialog(mContext = requireContext(), isCancelable = true, isBackCancelable = true)

        viewModel.getEpisodesVersionList(0L,20L)
        lifecycleScope.launchWhenCreated {
            viewModel.episodesVersionListStateFlow.collect{
                slideDialog.list = it
                viewBinding.textTitle.text = if (it.isNotEmpty()) it[0] else "本周榜"
                if (it.isNotEmpty()){
                    val str = it[0]
                    val version = it[0].substring(str.indexOf("第") + "第".length,str.indexOf("期")).toInt()
                    viewModel.getEpisodesList(version)
                }
            }
        }

        viewModel.viewModelScope.launch {
            viewModel.episodesListStateFlow.collect{
                viewBinding.episodesList.adapter = EpisodeListAdapter(it)
                viewBinding.episodesList.adapter?.notifyItemRangeChanged(0,it.size)
            }
        }

        viewBinding.textTitle.setOnClickListener {
            slideDialog.show()
        }

        slideDialog.setOnSelectListener(object : SlideDialog.OnSelectListener{
            override fun onCancel() {}

            override fun onAgree(txt: String, pos: Int) {
                viewModel.getEpisodesList(version = viewModel.episodesVersionList[pos])
                viewBinding.textTitle.text = txt
            }
        })

        viewBinding.imageRefresh.setOnClickListener {
            viewModel.getEpisodesVersionList(0L,20L,true)
        }
    }
}