package com.example.personal_mine.bean
data class MineResponse(
    val `data`: Data
)

data class Data(
    val avatar: String,
    val city: String,
    val country: String,
    val description: String,
    val e_account_role: String,
    val error_code: String,
    val gender: String,
    val nickname: String,
    val open_id: String,
    val province: String,
    val union_id: String
)