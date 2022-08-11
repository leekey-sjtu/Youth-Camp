package com.example.personal_mine.repository

import android.util.Log
import com.example.common.base.constants.TokenConstants
import com.example.personal.service.friend.FriendNetWork
import com.example.personal_mine.network.MineNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object MineRepository {
    private const val TAG = "MineRepository"
    fun getMine() = flow {
        val mineResponse = MineNetWork.getMineData()
        if (mineResponse.data.error_code == "0") {
            Log.e(TAG, "getMine: ${mineResponse.data}", )
            emit(mineResponse.data)
        }else{
            Log.e(TAG, "getMine:" +
                    "id ${TokenConstants.OPEN_ID}   token  " +
                    "${TokenConstants.ACCESS_TOKEN}" +
                    "" +
                    " ${mineResponse.data}", )
        }
    }.flowOn(Dispatchers.IO)

    fun getFans() = flow{
        val mineFansResponse = FriendNetWork.getFansList(0)
        if (mineFansResponse.data.error_code == 0){
            emit(mineFansResponse.data.total)
        }
    }

    fun getFollow() = flow {
        val mineFollowsResponse = FriendNetWork.getFollowList(0)
        if (mineFollowsResponse.data.error_code == 0){
            Log.e(TAG, "getFollow: ￥${mineFollowsResponse.data}", )
            emit(mineFollowsResponse.data.total)
        }
    }
}
