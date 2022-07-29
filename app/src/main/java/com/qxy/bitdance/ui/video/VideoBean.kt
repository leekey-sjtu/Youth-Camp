package com.qxy.bitdance.ui.video

data class VideoBean(
    val feeds: List<Feed>,
    val success: Boolean
)

data class Feed(
    val _id: String,
    val createdAt: String,
    val image_h: Int,
    val image_url: String,
    val image_w: Int,
    val student_id: String,
    val updatedAt: String,
    val user_name: String,
    val video_url: String
)