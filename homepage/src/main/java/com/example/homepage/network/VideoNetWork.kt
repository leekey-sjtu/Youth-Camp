package com.example.homepage.network

import com.example.homepage.service.VideoService

object VideoNetWork {

    private val videoService = getRetrofit("https://bd-open-lesson.bytedance.com/api/invoke/")
        .create(VideoService::class.java)

    suspend fun getVideoList(cursor : Int)
            = videoService.getVideoList("121110910068_portrait")

}