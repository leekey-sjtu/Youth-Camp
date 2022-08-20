package com.example.homepage.service

import com.example.homepage.bean.CovidResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidService {

    @GET("list")
    suspend fun getCovid(
        @Query("modules") modules: String
    ): CovidResponse

}