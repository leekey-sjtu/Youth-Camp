package com.example.hotlist.bean

data class HotListVersionResponse (
    val extra: Extra,
    val data: VData
)

data class VData(
        val list: List<VListItem>,  // 榜单版本列表
        val cursor: Long,       // 用于下一页请求的cursor
        val description: String,// 错误码描述
        val error_code: Long,   // 错误码	0
        val has_more: Boolean
)

data class VListItem(
    val active_time: String,        // 榜单生成时间	2020-03-30 12:00:00
    val end_time: String,           // 榜单结束时间	2020-03-30 12:00:00
    val start_time: String,         // 榜单开始时间	2020-03-30 12:00:00
    val type: Int,                  // 榜单类型:1=电影 2=电视剧 3=综艺	1
    val version: Int                // 榜单版本	1
)