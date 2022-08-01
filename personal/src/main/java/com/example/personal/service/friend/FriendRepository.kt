package com.example.personal.service.friend

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FriendRepository {

    private companion object {
        const val TAG = "FriendRepository"
    }

    fun getFollowList(cursor : Int) = flow {
        val followResponse = FriendNetWork.getFollowList(cursor)
        if (followResponse.data.error_code == 0) emit(followResponse.data)
        else Log.e(TAG,"getFollowList请求错误，错误码：$followResponse.data.error_code")
    }.flowOn(Dispatchers.IO)

    fun getFansList(cursor : Int) = flow {
        val fansResponse = FriendNetWork.getFansList(cursor)
        if (fansResponse.data.error_code == 0) emit(fansResponse.data)
        else Log.e(TAG,"getFollowList请求错误，错误码：$fansResponse.data.error_code")
    }.flowOn(Dispatchers.IO)

}