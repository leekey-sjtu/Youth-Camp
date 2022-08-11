package com.qxy.bitdance.test

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatListRepository {
    private companion object {
        const val TAG = "CatListRepository"
    }

/*
    fun getCatList() = flow {
        val catListResponse = CatListNetWork.getCatList()
        Log.e(TAG, "getCatList: ${catListResponse.sub} 111")
        if (catListResponse.code == 200) emit(catListResponse.sub)
        else Log.d("getCatList", "网络请求错误！")
    }.flowOn(Dispatchers.IO)
*/

}