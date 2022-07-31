package com.example.hotlist.ui.films

import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseViewModel
import com.example.hotlist.bean.ListItem
import com.example.hotlist.bean.VListItem
import com.example.hotlist.service.HotListRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FilmsListViewModel: BaseViewModel() {
    private val hotListRepository = HotListRepository()
    var filmsList: List<ListItem> = listOf()
    var filmsListVersion: List<VListItem> = listOf()

    fun getHotList(type: Int, version: Int): List<ListItem> {
        viewModelScope.launch {
            hotListRepository.getHotList(type = type, version = version).collect {
                filmsList = it
            }
        }
        return filmsList
    }

    fun getHotListVersion(cursor: Long, count: Long, type: Int): List<VListItem> {
        viewModelScope.launch {
            hotListRepository.getHotListVersion(cursor = cursor, count = count,type = type).collect {
                filmsListVersion = it
            }
        }
        return filmsListVersion
    }

    fun getHotList(cursor: Long,count: Long,type: Int): List<ListItem>{
        viewModelScope.launch {
            hotListRepository.getHotListVersion(cursor = cursor, count = count,type = type).collect {
                hotListRepository.getHotList(type = type, version = it[0].version).collect {
                    filmsList = it
                }
            }
        }
        return filmsList
    }
}