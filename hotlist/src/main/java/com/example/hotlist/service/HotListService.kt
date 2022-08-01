package com.example.hotlist.service

import com.example.hotlist.bean.HotListResponse
import com.example.hotlist.bean.HotListVersionResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface HotListService {
    @GET("discovery/ent/rank/item/")
    suspend fun getHotList(
        @Query("type") type: Int,
        @Query("version") version: Int,
        @Header("access-token") accessToken: String
    ): HotListResponse

    @GET("discovery/ent/rank/version/")
    suspend fun getHotListVersion(
        @Query("cursor") cursor: Long,
        @Query("count") count: Long,
        @Query("type") type: Int,
        @Header("access-token") accessToken: String
    ): HotListVersionResponse
}