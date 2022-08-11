package com.example.personal_mine.repository

import android.media.MediaRecorder
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.base.network.RetrofitClient
import com.example.personal_mine.bean.Video
import com.example.personal_mine.service.VideoMineService
import com.example.personal_mine.source.VideoPagingSource
import kotlinx.coroutines.flow.Flow

object VideoMineRepository {
    private const val PAGE_SIZE = 20
    private val videoService = RetrofitClient.retrofit.create(VideoMineService::class.java)

    fun getVideos(openId:String): Flow<PagingData<Video>>{
        return Pager(
            config = PagingConfig(20),
            pagingSourceFactory = {
               VideoPagingSource(videoService,openId)
            }
        ).flow
    }
}