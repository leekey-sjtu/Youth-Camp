package com.qxy.bitdance.test

import com.qxy.bitdance.NetWork.ApiResponse
import com.qxy.bitdance.NetWork.RetrofitClient
import retrofit2.create
import com.qxy.bitdance.NetWork.BaseRepository

class TestNetWork : BaseRepository()  {

    private val catListService = RetrofitClient.retrofit.create<CatListService>()

    suspend fun getCatList() : ApiResponse<CatListResponse>{
        return executeHttp {
            catListService.getCatList()
        }
    }

}