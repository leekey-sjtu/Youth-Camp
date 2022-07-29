package com.example.common.base.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL: String = "https://open.douyin.com/"
//    private const val BASE_URL: String = "https://netease-cloud-music-api-4eodv9lwk-tangan91314.vercel.app/"

    private val client : OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .addInterceptor(LogInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
        builder.build()
    }

    val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}