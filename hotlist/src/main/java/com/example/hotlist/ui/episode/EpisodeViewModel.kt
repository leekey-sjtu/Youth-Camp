package com.example.hotlist.ui.episode

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseViewModel
import com.example.common.base.constants.TokenConstants
import com.example.common.base.service.token.TokenRepository
import com.example.hotlist.bean.ListItem
import com.example.hotlist.constants.HotListConstants
import com.example.hotlist.service.HotListRepository
import com.example.hotlist.utils.HotListUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EpisodeViewModel: BaseViewModel() {
    private val _episodesListStateFlow = MutableStateFlow<List<ListItem>>(emptyList())
    val episodesListStateFlow: StateFlow<List<ListItem>> = _episodesListStateFlow

    fun getEpisodesList(version: Int) {
        viewModelScope.launch {
            HotListRepository.getHotList(type = HotListConstants.EPISODE, version = version).collect{
                _episodesListStateFlow.value = it.list!!
            }
        }
    }

    private val _episodesVersionListStateFlow = MutableStateFlow<MutableList<String>>(mutableListOf())
    val episodesVersionListStateFlow: StateFlow<List<String>> = _episodesVersionListStateFlow

    val episodesVersionList = mutableListOf<Int>()

    fun getEpisodesVersionList(cursor: Long, size: Long) {
        viewModelScope.launch {
            HotListRepository.getHotListVersion(cursor = cursor, count = size, type = HotListConstants.EPISODE).collect{
                it.list.forEach { item ->
                    episodesVersionList.add(item.version)
                    "第${item.version}期 ${HotListUtil.formatDate(item.start_time)}-${HotListUtil.formatDate(item.end_time)}".let { res ->
                        _episodesVersionListStateFlow.value.add(res)
                    }
                }
            }
        }
    }
}