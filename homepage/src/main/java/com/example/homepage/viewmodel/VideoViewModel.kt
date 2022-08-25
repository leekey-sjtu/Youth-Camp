package com.example.homepage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseViewModel
import com.example.homepage.bean.Feed
import com.example.homepage.repository.VideoRepository
import kotlinx.coroutines.launch

class VideoViewModel : BaseViewModel() {

    private val videoRepository = VideoRepository()
    val videoListData = MutableLiveData<List<Feed>>()

    fun getVideoList(cursor: Int) {
        viewModelScope.launch {
            videoRepository.getVideoList(cursor).collect {
                videoListData.value = it
            }
        }
    }


}