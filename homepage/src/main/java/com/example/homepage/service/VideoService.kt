package com.example.homepage.service

import com.example.homepage.bean.VideoResponse
import retrofit2.Call
import retrofit2.http.*

interface VideoService {

    @GET("video")
    suspend fun getVideoList(
        @Query("student_id") student_id: String
    ) : VideoResponse

}