package com.example.homepage.network

import com.example.homepage.bean.NewsResponse
import com.example.homepage.service.NewsService
import com.example.homepage.utils.NetworkUtils.getRetrofit

object NewsNetWork {

    private const val BASE_URL ="https://way.jd.com/jisuapi/"

    suspend fun getNews(cursor: Int): NewsResponse {
        return getRetrofit(BASE_URL)
            .create(NewsService::class.java)
            .getNews(
                "头条",
                "10",
                "0",
                "92b9f9e7465ed6a8a72e27330aa8310a" // TODO: appkey需要隐藏
            )
    }

}