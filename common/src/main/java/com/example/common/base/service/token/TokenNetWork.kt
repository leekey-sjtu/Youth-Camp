package com.example.common.base.service.token

import com.example.common.base.constants.TokenConstants
import com.example.common.base.network.RetrofitClient
import com.example.common.base.service.AccessTokenService

object TokenNetWork {
    private val tokenService = RetrofitClient.retrofit.create(AccessTokenService::class.java)

    suspend fun getClientToken(clientKey: String = TokenConstants.CLIENT_KEY, clientSecret: String = TokenConstants.CLIENT_SECRET)
        = tokenService.getClientToken(client_key = clientKey, client_secret = clientSecret, grant_type = "client_credential")

}