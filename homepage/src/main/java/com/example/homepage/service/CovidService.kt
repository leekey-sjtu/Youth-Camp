package com.example.homepage.service

import com.example.homepage.bean.CovidBean.COVID19Bean
import retrofit2.Call
import retrofit2.http.*

interface CovidService {

    @GET("list")
    fun getData(@Query("modules") modules: String): Call<COVID19Bean>

}