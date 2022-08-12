package com.example.personal_mine.service


import com.example.personal_mine.bean.MineResponse
import retrofit2.http.*

interface MineService {
    @FormUrlEncoded
    @POST("oauth/userinfo/")
    suspend fun getMine(
        @Field("open_id") openId:String,
        @Field("access_token") accessToken: String,):MineResponse

}