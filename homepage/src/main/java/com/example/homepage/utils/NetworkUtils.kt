package com.example.homepage.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkUtils {

    fun getRetrofit(baseUrl: String): Retrofit {  // 设置新的baseUrl
        val client = OkHttpClient.Builder()
            .connectTimeout(5000, TimeUnit.MILLISECONDS)  // 预留时间连接服务器
            .readTimeout(5000, TimeUnit.MILLISECONDS)  // 预留时间处理数据，否则偶尔出现超时java.net.SocketTimeoutException: timeout
            .writeTimeout(5000, TimeUnit.MILLISECONDS)
            .build()

        val factory = GsonConverterFactory.create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(factory)
            .build()
    }

}