package com.example.homepage.repository

import android.util.Log
import com.example.homepage.network.NewsNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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