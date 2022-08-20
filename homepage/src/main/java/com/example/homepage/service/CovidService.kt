package com.example.homepage.service

import com.example.homepage.bean.CovidResponse
import retrofit2.Call
import retrofit2.http.*

interface CovidService {

    @GET("list")
    suspend fun getCovid(
        @Query("modules") modules: String
    ): CovidResponse

}