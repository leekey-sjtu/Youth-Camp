package com.example.homepage.service

import com.example.homepage.bean.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("get")
    suspend fun getNews(
        @Query("channel") channel: String,
        @Query("num") num: String,
        @Query("start") start: String,
        @Query("appkey") appkey: String
    ): NewsResponse

}