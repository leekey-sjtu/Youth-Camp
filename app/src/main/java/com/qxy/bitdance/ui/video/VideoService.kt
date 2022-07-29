package com.qxy.bitdance.ui.video

import retrofit2.Call
import retrofit2.http.*

interface VideoService {
    //获取视频
    @GET("video")
    fun getVideo(@Query("student_id") student_id: String): Call<VideoBean>
}