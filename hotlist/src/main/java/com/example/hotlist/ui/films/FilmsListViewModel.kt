package com.example.hotlist.ui.films

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseViewModel
import com.example.common.base.constants.TokenConstants
import com.example.common.base.service.token.TokenRepository
import com.example.hotlist.bean.ListItem
import com.example.hotlist.bean.VListItem
import com.example.hotlist.constants.HotListConstants
import com.example.hotlist.service.HotListRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FilmsListViewModel: BaseViewModel() {
    // 单向数据流的形式处理
    private val _cursorFlow = MutableStateFlow(1L)
    private val _countFLow = MutableStateFlow(1L)
    private val _filmsFlow = MutableStateFlow<MutableList<ListItem>>(mutableListOf())

    private val cursorFlow: StateFlow<Long> = _cursorFlow
    private val countFlow: StateFlow<Long> = _countFLow
    val filmsFlow: StateFlow<List<ListItem>> = _filmsFlow


    suspend fun updateNextPageFilmsList(){
        if (TokenConstants.CLIENT_TOKEN != ""){
            getFilmList()
        }else{
            TokenRepository.getClientToken().collect{
                TokenConstants.CLIENT_TOKEN = it
                getFilmList()
            }
        }
    }

    private suspend fun getFilmList(){
        HotListRepository.getHotListVersion(cursor = cursorFlow.value, count = countFlow.value, type = HotListConstants.FIlM).collect {vData->
            Log.e("wgw", "updateNextPageFilmsList: $vData", )
            _cursorFlow.value = vData.cursor
            vData.list.forEach { vListItem ->
                Log.e("wgw", "updateNextPageFilmsList: $vListItem", )
                HotListRepository.getHotList(type = HotListConstants.FIlM, version = vListItem.version).collect{filmList ->
                    _filmsFlow.value.addAll(filmList)
                }
            }
        }
    }
}