package com.example.homepage.repository

import android.util.Log
import com.example.homepage.adapter.VideoAdapter
import com.example.homepage.bean.VideoResponse
import com.example.homepage.network.VideoNetWork
import com.example.homepage.network.getRetrofit
import com.example.homepage.service.VideoService
import com.example.homepage.utils.myLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoRepository {

    private companion object {
        const val TAG = "VideoRepository"
    }

    fun getVideoList(cursor: Int) = flow {
        val videoResponse = VideoNetWork.getVideoList(cursor)
        if (videoResponse.success) emit(videoResponse.feeds)
        else Log.e(TAG,"get video list failed")
    }.flowOn(Dispatchers.IO)

}