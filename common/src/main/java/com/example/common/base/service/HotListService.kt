package com.example.common.base.service

import com.example.common.base.bean.HotListResponse
import com.example.common.base.bean.HotListTokenResponse
import retrofit2.Call
import retrofit2.http.*

interface HotListService {

    @GET("/oauth/client_token/")
    fun getClientToken(
        @Query("client_key") client_key : String,
        @Query("client_secret") client_secret : String,
        @Query("grant_type") grant_type : String,
    ) : Call<HotListTokenResponse>

    @GET("/discovery/ent/rank/item/")
    fun getHotList(
        @Header("access-token") client_access_token : String,
        @Query("type") type : Int,
        @Query("version") version : Int,
    ): Call<HotListResponse>

}