package com.example.personal_mine.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.common.base.baseui.BaseViewModel
import com.example.personal_mine.bean.MineVideo
import com.example.personal_mine.repository.VideoMineRepository
import kotlinx.coroutines.flow.Flow

class VideoMineViewModel:BaseViewModel(){
    fun getVideoData(openId:String): Flow<PagingData<MineVideo>>{
        return VideoMineRepository.getVideos(openId).cachedIn(viewModelScope)
    }
}