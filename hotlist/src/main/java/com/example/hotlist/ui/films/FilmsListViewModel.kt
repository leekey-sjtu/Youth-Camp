package com.example.hotlist.ui.films

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseViewModel
import com.example.common.base.constants.TokenConstants
import com.example.common.base.service.token.TokenRepository
import com.example.hotlist.bean.ListItem
import com.example.hotlist.bean.VData
import com.example.hotlist.bean.VListItem
import com.example.hotlist.constants.HotListConstants
import com.example.hotlist.service.HotListRepository
import com.example.hotlist.utils.HotListUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FilmsListViewModel: BaseViewModel() {
    private val _filmsListStateFlow = MutableStateFlow<List<ListItem>>(emptyList())
    val filmsListStateFlow: StateFlow<List<ListItem>> = _filmsListStateFlow

    fun getFilmsList(version: Int) {
        viewModelScope.launch {
            HotListRepository.getHotList(type = HotListConstants.FIlM, version = version).collect{
                _filmsListStateFlow.value = it.list!!
            }
        }
    }

    private val _filmsVersionListStateFlow = MutableStateFlow<MutableList<String>>(mutableListOf())
    val filmsVersionListStateFlow: StateFlow<List<String>> = _filmsVersionListStateFlow

    val filmsVersionList = mutableListOf<Int>()

    fun getFilmsVersionList(cursor: Long, size: Long,getFromNetwork: Boolean = false) {
        viewModelScope.launch {
            HotListRepository.getHotListVersion(cursor = cursor, count = size, type = HotListConstants.FIlM,getFromNetwork = getFromNetwork).collect{
                val tempRes = mutableListOf<String>()
                it.list.forEach { item ->
                    filmsVersionList.add(item.version)
                    "第${item.version}期 ${HotListUtil.formatDate(item.start_time)}-${HotListUtil.formatDate(item.end_time)}".let { res ->
                        tempRes.add(res)
                    }
                }
                _filmsVersionListStateFlow.value = tempRes
            }
        }
    }
}