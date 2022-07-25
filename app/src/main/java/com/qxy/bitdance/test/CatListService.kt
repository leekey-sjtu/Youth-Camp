package com.qxy.bitdance.test

import com.qxy.bitdance.network.ApiResponse
import retrofit2.http.GET

interface CatListService {
    @GET("playlist/catlist")
    suspend fun getCatList(): ApiResponse<CatListResponse>
}