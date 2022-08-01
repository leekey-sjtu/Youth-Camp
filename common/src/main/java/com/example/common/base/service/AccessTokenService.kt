package com.example.common.base.service

import com.example.common.base.bean.AccessTokenResponse
import com.example.common.base.bean.ClientTokenResponse
import retrofit2.Call
import retrofit2.http.*

interface AccessTokenService {
    @GET("/oauth/access_token")
    fun getAccessToken(
        @Query("client_secret") client_secret : String,
        @Query("code") auth_code : String,
        @Query("grant_type") grant_type : String,
        @Query("client_key") client_key : String,
    ): Call<AccessTokenResponse>

    @GET("/oauth/client_token")
    suspend fun getClientToken(
        @Query("client_secret") client_secret : String,
        @Query("client_key") client_key : String,
        @Query("grant_type") grant_type : String
    ): ClientTokenResponse
}