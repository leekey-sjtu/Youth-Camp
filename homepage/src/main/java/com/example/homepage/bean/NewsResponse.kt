package com.example.homepage.bean;

data class NewsResponse(
    val charge: Boolean,
    val code: String,
    val msg: String,
    val requestId: String,
    val result: NewsResult1
)

data class NewsResult1(
    val msg: String,
    val result: NewsResult2,
    val status: Int
)

data class NewsResult2(
    val channel: String,
    val list: List<NewsInfo>,
    val num: Int
)

data class NewsInfo(
    val category: String,
    val content: String,
    val pic: String,
    val src: String,
    val time: String,
    val title: String,
    val url: String,
    val weburl: String
)