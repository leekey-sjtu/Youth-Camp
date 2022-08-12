package com.example.upload.bean

data class ReleaseBean(
    val data: ReleaseData,
    val extra: Extra
)

data class ReleaseData(
    val description: String,
    val error_code: Int,
    val item_id: String
)

data class Release(
    val text : String,
    val image_list : List<String>
)