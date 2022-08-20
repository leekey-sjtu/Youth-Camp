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

    fun getSkyCondition(skycon: String): String {
        when (skycon) {
            "CLEAR_DAY" -> return "晴"
            "CLEAR_NIGHT" -> return "晴"
            "PARTLY_CLOUDY_DAY" -> return "多云"
            "PARTLY_CLOUDY_NIGHT" -> return "多云"
            "CLOUDY" -> return "阴"
            "LIGHT_HAZE" -> return "轻度雾霾"
            "MODERATE_HAZE" -> return "中度雾霾"
            "HEAVY_HAZE" -> return "重度雾霾"
            "LIGHT_RAIN" -> return "小雨"
            "MODERATE_RAIN" -> return "中雨"
            "HEAVY_RAIN" -> return "大雨"
            "STORM_RAIN" -> return "暴雨"
            "FOG" -> return "雾"
            "LIGHT_SNOW" -> return "小雪"
            "MODERATE_SNOW" -> return "中雪"
            "HEAVY_SNOW" -> return "大雪"
            "STORM_SNOW" -> return "暴雪"
            "DUST" -> return "浮尘"
            "SAND" -> return "沙尘"
            "WIND" -> return "大风"
        }
        return ""
    }

}