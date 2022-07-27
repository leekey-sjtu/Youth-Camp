package com.qxy.bitdance.test

import retrofit2.http.GET

interface CatListService{

    @GET("playlist/catlist")
    suspend fun getCartList() : CatListResponse

}