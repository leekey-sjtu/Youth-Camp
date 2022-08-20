package com.example.homepage.repository

import android.util.Log
import com.example.homepage.adapter.VideoAdapter
import com.example.homepage.bean.VideoResponse
import com.example.homepage.bean.WeatherResponse
import com.example.homepage.network.NewsNetWork
import com.example.homepage.network.VideoNetWork
import com.example.homepage.network.WeatherNetWork
import com.example.homepage.service.VideoService
import com.example.homepage.utils.myLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    private companion object {
        const val TAG = "WeatherRepository"
    }

    fun getWeather(cursor: Int) = flow {
        val weatherResponse = WeatherNetWork.getWeather(cursor)
        if (weatherResponse.status == "ok") emit(weatherResponse.result)
        else Log.e(TAG,"get weather failed -> status = ${weatherResponse.status}")
    }.flowOn(Dispatchers.IO)

}