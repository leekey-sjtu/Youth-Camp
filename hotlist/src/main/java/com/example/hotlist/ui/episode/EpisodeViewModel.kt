package com.example.hotlist.ui.episode

import android.util.Log
import com.example.common.base.baseui.BaseViewModel
import com.example.common.base.constants.TokenConstants
import com.example.common.base.service.token.TokenRepository
import com.example.hotlist.bean.ListItem
import com.example.hotlist.constants.HotListConstants
import com.example.hotlist.service.HotListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EpisodeViewModel: BaseViewModel() {
    // 单向数据流的形式处理
    private val _cursorFlow = MutableStateFlow(1L)
    private val _countFLow = MutableStateFlow(1L)
    private val _episodesFlow = MutableStateFlow<MutableList<ListItem>>(mutableListOf())

    private val cursorFlow: StateFlow<Long> = _cursorFlow
    private val countFlow: StateFlow<Long> = _countFLow
    val episodesFlow: StateFlow<List<ListItem>> = _episodesFlow


    suspend fun updateNextPageEpisodesList(){
        if (TokenConstants.CLIENT_TOKEN != ""){
            getEpisodesList()
        }else{
            TokenRepository.getClientToken().collect{
                TokenConstants.CLIENT_TOKEN = it
                getEpisodesList()
            }
        }
    }

    private suspend fun getEpisodesList(){
        HotListRepository.getHotListVersion(cursor = cursorFlow.value, count = countFlow.value, type = HotListConstants.EPISODE).collect { vData->
            Log.e("wgw", "updateNextPageFilmsList: $vData", )
            _cursorFlow.value = vData.cursor
            vData.list.forEach { vListItem ->
                Log.e("wgw", "updateNextPageFilmsList: $vListItem", )
                HotListRepository.getHotList(type = HotListConstants.EPISODE, version = vListItem.version).collect{ episodeList ->
                    _episodesFlow.value.addAll(episodeList)
                }
            }
        }
    }
}