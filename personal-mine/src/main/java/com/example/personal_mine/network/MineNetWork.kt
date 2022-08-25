package com.example.personal_mine.network

import com.example.common.base.constants.TokenConstants
import com.example.common.base.network.RetrofitClient
import com.example.personal_mine.service.MineService
import com.example.personal_mine.service.VideoMineService
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Query

object MineNetWork {
    private val mineService = RetrofitClient.retrofit.create(MineService::class.java)

    suspend fun getMineData() =
        mineService.getMine(TokenConstants.OPEN_ID, TokenConstants.ACCESS_TOKEN)



}