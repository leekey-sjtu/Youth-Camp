package com.example.hotlist.ui.variety

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

class VarietyListViewModel: BaseViewModel() {
    private val _varietiesListStateFlow = MutableStateFlow<List<ListItem>>(emptyList())
    val varietiesListStateFlow: StateFlow<List<ListItem>> = _varietiesListStateFlow

    fun getVarietiesList(version: Int) {
        viewModelScope.launch {
            HotListRepository.getHotList(type = HotListConstants.VARIETY, version = version).collect{
                _varietiesListStateFlow.value = it.list!!
            }
        }
    }

    private val _varietiesVersionListStateFlow = MutableStateFlow<MutableList<String>>(mutableListOf())
    val varietiesVersionListStateFlow: StateFlow<List<String>> = _varietiesVersionListStateFlow

    val varietiesVersionList = mutableListOf<Int>()

    fun getVarietiesVersionList(cursor: Long, size: Long) {
        viewModelScope.launch {
            HotListRepository.getHotListVersion(cursor = cursor, count = size, type = HotListConstants.VARIETY).collect{
                it.list.forEach { item ->
                    varietiesVersionList.add(item.version)
                    "第${item.version}期 ${HotListUtil.formatDate(item.start_time)}-${HotListUtil.formatDate(item.end_time)}".let { res ->
                        _varietiesVersionListStateFlow.value.add(res)
                    }
                }
            }
        }
    }
}