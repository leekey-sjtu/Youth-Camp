package com.example.common.base.service

import com.example.common.base.bean.AccessTokenResponse
import retrofit2.Call
import retrofit2.http.*

interface AccessTokenService {
    @GET("/oauth/access_token")
    fun getAccessToken(
        @Query("client_secret") client_secret : String,
        @Query("code") code : String,
        @Query("grant_type") grant_type : String,
        @Query("client_key") client_key : String,
    ): Call<AccessTokenResponse>
}