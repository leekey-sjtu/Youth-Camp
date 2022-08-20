package com.example.homepage.repository

import android.util.Log
import com.example.homepage.network.WeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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