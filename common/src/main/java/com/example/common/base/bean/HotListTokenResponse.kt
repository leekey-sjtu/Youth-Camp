package com.example.common.base.bean

data class HotListTokenResponse(
    val data: HotListToken,
    val message: String
)

data class HotListToken(
    val access_token: String,
    val description: String,
    val error_code: String,
    val expires_in: String
)