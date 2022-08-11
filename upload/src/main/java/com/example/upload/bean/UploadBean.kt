package com.example.upload.bean

data class UploadBean(
    val data : UploadData,
    val extra: Extra
)

data class UploadData(
    val description: String,
    val error_code: Int,
    val image: Image
)

data class Extra(
    val description: String,
    val error_code: Int,
    val logid: String,
    val now: Long,
    val sub_description: String,
    val sub_error_code: Int
)

data class Image(
    val height: Int,
    val image_id: String,
    val width: Int
)