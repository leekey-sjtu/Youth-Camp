package com.example.personal_mine.bean



data class VideoMineResponse(
    val `data`: VideoData,
    val extra: Extra
)

data class VideoData(
    val cursor: Int,
    val description: String,
    val error_code: Int,
    val has_more: Boolean,
    val list: List<Video>
)

data class Extra(
    val description: String,
    val error_code: Int,
    val logid: String,
    val now: Long,
    val sub_description: String,
    val sub_error_code: Int
)

data class Video(
    val cover: String,
    val create_time: Int,
    val is_reviewed: Boolean,
    val is_top: Boolean,
    val item_id: String,
    val media_type: Int,
    val share_url: String,
    val statistics: Statistics,
    val title: String,
    val video_id: String,
    val video_status: Int
)

data class Statistics(
    val comment_count: Int,
    val digg_count: Int,
    val download_count: Int,
    val forward_count: Int,
    val play_count: Int,
    val share_count: Int
)