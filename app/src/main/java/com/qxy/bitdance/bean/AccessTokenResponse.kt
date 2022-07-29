package com.qxy.bitdance.bean

data class AccessTokenResponse(
    val data: Data,
    val message: String
)

data class Data(
    val access_token: String,
    val description: String,
    val error_code: String,
    val expires_in: String,
    val open_id: String,
    val refresh_expires_in: String,
    val refresh_token: String,
    val scope: String
)