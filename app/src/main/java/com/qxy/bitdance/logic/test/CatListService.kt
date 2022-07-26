package com.qxy.bitdance.logic.test

import com.qxy.bitdance.logic.network.ApiResponse
import retrofit2.http.GET

interface CatListService {
    @GET("playlist/catlist")
    suspend fun getCatList(): ApiResponse<CatListResponse>
}