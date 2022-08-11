package com.example.personal_mine.service

import com.example.common.base.constants.TokenConstants
import com.example.personal_mine.bean.VideoMineResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface VideoMineService {
    @GET("video/list")
    suspend fun getVideoList(
        @Header("access-token")   accessToken :String =  TokenConstants.ACCESS_TOKEN,
        @Query("open_id") openId: String,
        @Query("cursor") cursor: Long = 0,
        @Query("count") count: Int =20
    ):VideoMineResponse


}