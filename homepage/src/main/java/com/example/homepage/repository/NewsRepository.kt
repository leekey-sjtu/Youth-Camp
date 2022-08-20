package com.example.homepage.repository

import android.os.Handler
import android.util.Log
import com.example.homepage.network.NewsNetWork
import com.example.homepage.ui.HomePageFragment.Companion.END_SWIPE_REFRESH_FOR_FAIL
import com.example.homepage.ui.HomePageFragment.Companion.END_SWIPE_REFRESH_FOR_SUCCESS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepository {

    private companion object {
        const val TAG = "NewsRepository"
    }

    fun getNews(cursor: Int, handlerSwipeRefresh: Handler) = flow {
        val newsResponse = NewsNetWork.getNews(cursor)
        if (newsResponse.result.status == 0) {
            emit(newsResponse.result.result.list)
            handlerSwipeRefresh.sendEmptyMessage(END_SWIPE_REFRESH_FOR_SUCCESS)
        }
        else {
            Log.e(TAG,"get news failed -> ${newsResponse.msg}")
            handlerSwipeRefresh.sendEmptyMessage(END_SWIPE_REFRESH_FOR_FAIL)
        }
    }.flowOn(Dispatchers.IO)

}