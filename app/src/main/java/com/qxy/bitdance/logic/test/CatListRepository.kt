package com.qxy.bitdance.logic.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object CatListRepository {

    private val repository by lazy { TestNetWork() }

    fun getCatList(): Flow<List<Sub>> =
        flow {
            val catListResponse = repository.getCatList()
            if (catListResponse.isSuccess){
                emit(catListResponse.data!!.sub)
            }else{
                println(catListResponse.errorMsg)
            }
        }
            .flowOn(Dispatchers.IO)
            .catch {
            }
}