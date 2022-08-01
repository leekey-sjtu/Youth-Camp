package com.example.personal.service.friend

import com.example.personal.bean.FriendResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FriendService {

    @GET("following/list/")
    suspend fun getFollowList(
        @Header("access-token") accessToken : String,
        @Query("open_id") openId : String,
        @Query("cursor") cursor : Int,
        @Query("count") count : Int) : FriendResponse

    @GET("fans/list/")
    suspend fun getFansList(
        @Header("access-token") accessToken : String,
        @Query("open_id") openId : String,
        @Query("cursor") cursor : Int,
        @Query("count") count : Int) : FriendResponse

}