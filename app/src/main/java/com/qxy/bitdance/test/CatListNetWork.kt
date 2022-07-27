package com.qxy.bitdance.test

import com.qxy.bitdance.logic.network.RetrofitClient

object CatListNetWork {

    private val catListService = RetrofitClient.retrofit.create(CatListService::class.java)

    suspend fun getCatList() = catListService.getCartList()
}