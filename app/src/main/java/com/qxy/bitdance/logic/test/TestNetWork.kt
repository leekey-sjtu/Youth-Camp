package com.qxy.bitdance.logic.test

import com.qxy.bitdance.logic.network.ApiResponse
import com.qxy.bitdance.logic.network.RetrofitClient
import retrofit2.create
import com.qxy.bitdance.logic.network.BaseRepository

class TestNetWork : BaseRepository()  {

    private val catListService = RetrofitClient.retrofit.create<CatListService>()

    suspend fun getCatList() : ApiResponse<CatListResponse> {
        return executeHttp {
            catListService.getCatList()
        }
    }

}