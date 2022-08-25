package com.example.personal_mine.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.base.network.RetrofitClient
import com.example.personal_mine.bean.MineVideo
import com.example.personal_mine.service.VideoMineService
import com.example.personal_mine.source.VideoPagingSource
import kotlinx.coroutines.flow.Flow

object VideoMineRepository {
    private const val PAGE_SIZE = 12
    private val videoService = RetrofitClient.retrofit.create(VideoMineService::class.java)

    fun getVideos(openId:String): Flow<PagingData<MineVideo>>{
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {
               VideoPagingSource(videoService,openId)
            }
        ).flow
    }
}