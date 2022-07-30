package com.example.common.base.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TokenProService {
    private const val baseUrl = "http://106.15.2.32:8888"
    private val retrofit: TokenService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TokenService::class.java)

    /**
     * 保存AccessToken
     */
    suspend fun saveAccessToken(accessToken: String, openId: String) {
        retrofit.saveAccessToken(accessToken,openId)
    }

    /**
     * 获取AccessToken
     */
    suspend fun getAccessToken(openId: String): String {
        retrofit.getAccessToken(openId).let {
            return it.date ?: ""
        }
    }

    /**
     * 保存RefreshToken
     */
    suspend fun saveRefreshToken(refreshToken: String, openId: String) {
        retrofit.saveRefreshToken(refreshToken, openId)
    }

    /**
     * 获取RefreshToken
     */
    suspend fun getRefreshToken(openId: String): String {
        retrofit.getRefreshToken(openId).let {
            return it.date ?: ""
        }
    }

    /**
     * 保存ClientSecret
     */
    suspend fun saveClientSecret(clientSecret: String) {
        retrofit.saveClientSecret(clientSecret)
    }

    /**
     * 获取ClientSecret
     */
    suspend fun getClientSecret(): String {
        retrofit.getClientSecret().let {
            return it.date ?: ""
        }
    }

    /**
     * 保存其他Value
     */
    suspend fun saveValue(key: String, value: String,openId: String) {
        retrofit.saveValue(key, value, openId)
    }

    /**
     * 获取其他Value
     */
    suspend fun getValue(key: String, openId: String): String {
        retrofit.getValue(key, openId).let {
            return it.date ?: ""
        }
    }

}