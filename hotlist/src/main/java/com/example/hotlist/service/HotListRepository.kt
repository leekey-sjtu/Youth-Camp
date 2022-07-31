package com.example.hotlist.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HotListRepository {
    fun getHotList(type: Int, version: Int) = flow {
        val hotListResponse = HotListNetWork.getHotList(type, version)
        if (hotListResponse.data.error_code == 0L) {
            emit(hotListResponse.data.list)
        }
    }.flowOn(Dispatchers.IO)

    fun getHotListVersion(cursor: Long, count: Long, type: Int) = flow {
        val hotListVersionResponse = HotListNetWork.getHotListVersion(cursor, count, type)
        if (hotListVersionResponse.data.error_code == 0L) {
            emit(hotListVersionResponse.data.list)
        }
    }
}