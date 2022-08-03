package com.example.common.base.bean

data class ClientTokenResponse (
    val message: String,
    val data: ClientTokenData
)

data class ClientTokenData (
    val expires_in: Long,
    val access_token: String,
    val description: String,
    val error_code: Long
)