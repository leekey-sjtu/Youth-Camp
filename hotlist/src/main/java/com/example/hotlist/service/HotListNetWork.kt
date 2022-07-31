package com.example.hotlist.service

import com.example.common.base.network.RetrofitClient

object HotListNetWork {
    private val hotListService = RetrofitClient.retrofit.create(HotListService::class.java)

    suspend fun getHotList(type: Int, version: Int) = hotListService.getHotList(type = type, version = version)

    suspend fun getHotListVersion(cursor: Long, count: Long, type: Int) = hotListService.getHotListVersion(cursor = cursor, count = count, type = type)
}