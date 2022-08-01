package com.example.common.base.service.token

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object TokenRepository {
    fun getClientToken() = flow {
        val clientData = TokenNetWork.getClientToken()
        if (clientData.data.error_code == 0L){
            emit(clientData.data.access_token)
        }
    }.flowOn(Dispatchers.IO)
}