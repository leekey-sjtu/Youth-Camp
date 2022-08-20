package com.example.homepage.repository

import android.util.Log
import com.example.homepage.network.CovidNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CovidRepository {

    private companion object {
        const val TAG = "CovidRepository"
    }

    fun getCovid(cursor: Int) = flow {
        val covidResponse = CovidNetWork.getCovid(cursor)
        if (covidResponse.ret == 0) emit(covidResponse.data)
        else Log.e(TAG,"get covid failed -> ${covidResponse.info}")
    }.flowOn(Dispatchers.IO)

}