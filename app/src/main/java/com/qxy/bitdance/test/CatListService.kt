package com.qxy.bitdance.test

import com.qxy.bitdance.logic.network.BaseService
import retrofit2.http.GET

interface CatListService : BaseService {

    @GET("playlist/catlist")
    suspend fun getCartList() : CatListResponse

}