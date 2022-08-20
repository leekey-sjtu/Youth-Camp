package com.example.homepage.repository

import android.util.Log
import com.example.homepage.adapter.VideoAdapter
import com.example.homepage.bean.VideoResponse
import com.example.homepage.network.NewsNetWork
import com.example.homepage.network.VideoNetWork
import com.example.homepage.service.VideoService
import com.example.homepage.utils.myLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    private companion object {
        const val TAG = "NewsRepository"
    }

    fun getNews(cursor: Int) = flow {
        val newsResponse = NewsNetWork.getNews(cursor)
        if (newsResponse.result.status == 0) emit(newsResponse.result.result.list)
        else Log.e(TAG,"get news failed -> ${newsResponse.msg}")
    }.flowOn(Dispatchers.IO)

}