package com.example.personal_mine.repository

import android.util.Log
import com.example.common.base.constants.TokenConstants
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
}