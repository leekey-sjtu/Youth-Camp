package com.example.homepage.network

import com.example.homepage.bean.WeatherResponse
import com.example.homepage.service.WeatherService
import com.example.homepage.utils.NetworkUtils.getRetrofit

object WeatherNetWork {

    private const val BASE_URL = "https://api.caiyunapp.com/"

    suspend fun getWeather(cursor: Int): WeatherResponse {
        return getRetrofit(BASE_URL)
            .create(WeatherService::class.java)
            .getWeather(
                "101.6656",
                "39.2072",
                true,   // TODO： alert = true可以去掉
                "1",   // TODO：经纬度获取
                "24"
            )
    }

}