package com.example.common.base.bean

data class HotListResponse(
    val data: MovieData,
    val extra: Extra
)

data class MovieData(
    val active_time: String,
    val description: String,
    val error_code: String,
    val list: List<Movie>
)

data class Extra(
    val description: String,
    val error_code: String,
    val logid: String,
    val now: String,
    val sub_description: String,
    val sub_error_code: String
)

data class Movie(
    val actors: List<String>,
    val areas: List<String>,
    val directors: List<String>,
    val discussion_hot: String,
    val hot: String,
    val id: String,
    val influence_hot: String,
    val maoyan_id: String,
    val name: String,
    val name_en: String,
    val poster: String,
    val release_date: String,
    val search_hot: String,
    val tags: List<String>,
    val topic_hot: String,
    val type: String
)