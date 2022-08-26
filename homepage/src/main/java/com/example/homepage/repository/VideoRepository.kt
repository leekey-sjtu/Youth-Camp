package com.example.homepage.repository

import android.util.Log
import com.example.homepage.network.VideoNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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