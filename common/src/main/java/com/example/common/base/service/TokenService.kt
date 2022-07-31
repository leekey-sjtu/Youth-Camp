package com.example.common.base.service

import com.example.common.base.bean.TokenResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TokenService {
    @POST("/saveClientSecret/{clientSecret}")
    suspend fun saveClientSecret(@Path("clientSecret") clientSecret: String) : TokenResponse

    @GET("/getClientSecret")
    suspend fun getClientSecret() : TokenResponse

    @POST("/saveAccessToken/{accessToken}/{openId}")
    suspend fun saveAccessToken(@Path("accessToken") accessToken: String, @Path("openId") openId: String) : TokenResponse

    @GET("/getAccessToken/{openId}")
    suspend fun getAccessToken(@Path("openId") openId: String) : TokenResponse

    @POST("/saveRefreshToken/{refreshToken}/{openId}")
    suspend fun saveRefreshToken(@Path("refreshToken") refreshToken: String, @Path("openId") openId: String) : TokenResponse

    @GET("/getRefreshToken/{openId}")
    suspend fun getRefreshToken(@Path("openId") openId: String) : TokenResponse

    @POST("/saveValue/{key}/{value}/{openId}")
    suspend fun saveValue(@Path("key") key: String, @Path("value") value: String, @Path("openId") openId: String) : TokenResponse

    @GET("/getValue/{key}/{openId}")
    suspend fun getValue(@Path("key") key: String, @Path("openId") openId: String) : TokenResponse

    @GET("/saveClientKey/{clientKey}")
    suspend fun saveClientKey(@Path("clientKey") clientKey: String) : TokenResponse

    @GET("/getClientKey")
    suspend fun getClientKey() : TokenResponse
}