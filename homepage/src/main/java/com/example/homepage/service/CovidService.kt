package com.example.homepage.service

import com.example.homepage.bean.CovidResponse
import retrofit2.Call
import retrofit2.http.*

interface CovidService {

    @GET("list")
    fun getCovid(@Query("modules") modules: String): Call<CovidResponse>

}