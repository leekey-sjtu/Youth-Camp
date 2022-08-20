package com.example.homepage.network

import com.example.homepage.bean.VideoResponse
import com.example.homepage.service.VideoService
import com.example.homepage.utils.NetworkUtils.getRetrofit

object VideoNetWork {

    private const val BASE_URL = "https://bd-open-lesson.bytedance.com/api/invoke/"

    suspend fun getVideoList(cursor : Int): VideoResponse {
        return getRetrofit(BASE_URL)
            .create(VideoService::class.java)
            .getVideoList(
                "121110910068_portrait"  // TODO
            )
    }

}