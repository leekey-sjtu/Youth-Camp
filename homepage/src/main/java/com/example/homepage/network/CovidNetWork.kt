package com.example.homepage.network

import com.example.homepage.bean.CovidResponse
import com.example.homepage.bean.NewsResponse
import com.example.homepage.service.CovidService
import com.example.homepage.service.NewsService
import com.example.homepage.utils.NetworkUtils.getRetrofit

object CovidNetWork {

    private const val BASE_URL = "https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/"

    suspend fun getCovid(cursor: Int): CovidResponse {
        return getRetrofit(BASE_URL)
            .create(CovidService::class.java)
            .getCovid(
                "statisGradeCityDetail,diseaseh5Shelf"
            )
    }

}