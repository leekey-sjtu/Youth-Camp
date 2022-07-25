package com.qxy.bitdance.test

import com.qxy.bitdance.NetWork.ApiResponse
import retrofit2.http.GET

interface CatListService {
    @GET("playlist/catlis")
    suspend fun getCatList(): ApiResponse<CatListResponse>
}