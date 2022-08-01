package com.example.hotlist.ui.variety

import android.util.Log
import com.example.common.base.baseui.BaseViewModel
import com.example.common.base.constants.TokenConstants
import com.example.common.base.service.token.TokenRepository
import com.example.hotlist.bean.ListItem
import com.example.hotlist.constants.HotListConstants
import com.example.hotlist.service.HotListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VarietyListViewModel: BaseViewModel() {
    // 单向数据流的形式处理
    private val _cursorFlow = MutableStateFlow(1L)
    private val _countFLow = MutableStateFlow(1L)
    private val _varietiesFlow = MutableStateFlow<MutableList<ListItem>>(mutableListOf())

    private val cursorFlow: StateFlow<Long> = _cursorFlow
    private val countFlow: StateFlow<Long> = _countFLow
    val varietiesFlow: StateFlow<List<ListItem>> = _varietiesFlow


    suspend fun updateNextPageVarietiesList(){
        if (TokenConstants.CLIENT_TOKEN != ""){
            getVarietiesList()
        }else{
            TokenRepository.getClientToken().collect{
                TokenConstants.CLIENT_TOKEN = it
                getVarietiesList()
            }
        }
    }

    private suspend fun getVarietiesList(){
        HotListRepository.getHotListVersion(cursor = cursorFlow.value, count = countFlow.value, type = HotListConstants.VARIETY).collect { vData->
            Log.e("wgw", "updateNextPageFilmsList: $vData", )
            _cursorFlow.value = vData.cursor
            vData.list.forEach { vListItem ->
                Log.e("wgw", "updateNextPageFilmsList: $vListItem", )
                HotListRepository.getHotList(type = HotListConstants.VARIETY, version = vListItem.version).collect{ varietiesList ->
                    _varietiesFlow.value.addAll(varietiesList)
                }
            }
        }
    }
}