package com.example.homepage.service

import com.example.homepage.bean.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("v2.6/6BPYiRBxV7PoNzlc/{lng},{lat}/weather")
    fun getDailyWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String,
        @Query("alert") alert : Boolean,
        @Query("dailysteps") dailysteps : String,
        @Query("hourlysteps") hourlysteps : String
    ): Call<WeatherResponse>

}