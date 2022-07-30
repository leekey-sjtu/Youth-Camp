package com.example.common.base.bean

data class AccessTokenResponse(
    val data: TokenData,
    val message: String
)

data class TokenData(
    val access_token: String,
    val description: String,
    val error_code: String,
    val expires_in: String,
    val open_id: String,
    val refresh_expires_in: String,
    val refresh_token: String,
    val scope: String
)