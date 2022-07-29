package com.example.personal.bean

data class FriendResponse(
    val data : Data,
    val extra: Extra
)

data class Data(
    val cursor: Int,
    val description: String,
    val error_code: Int,
    val has_more: Boolean,
    val list: List<Friend>,
    val total: Int
)

data class Extra(
    val description: String,
    val error_code: Int,
    val logid: String,
    val now: Long,
    val sub_description: String,
    val sub_error_code: Int
)

data class Friend(
    val avatar: String,
    val city: String,
    val country: String,
    val gender: Int,
    val nickname: String,
    val open_id: String,
    val province: String,
    val union_id: String
)