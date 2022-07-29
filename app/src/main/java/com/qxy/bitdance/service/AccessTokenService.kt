package com.qxy.bitdance.service

import com.qxy.bitdance.bean.AccessTokenResponse
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
//    @Headers("Content-Type:application/x-www-form-urlencoded")
//    @POST("/oauth/access_token")
//    fun getAccessToken(
//        @Body postData : PostData
//        ): Call<AccessTokenResponse>
//    suspend fun getAccessToken(
//        @Body postData : PostData
//    ) : AccessTokenResponse
}

class PostData(
    val client_secret : String,
    val code : String,
    val grant_type : String,
    val client_key : String,
)