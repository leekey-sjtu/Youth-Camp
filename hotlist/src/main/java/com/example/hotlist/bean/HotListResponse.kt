package com.example.hotlist.bean

data class HotListResponse(
    val data: Data,
    val extra: Extra
)

data class Extra(
    val error_code: Long,       // 错误码	0
    val logid: String,          // 标识请求的唯一id	202008121419360101980821035705926A
    val now: Long,              // 毫秒级时间戳	1597213176393
    val sub_description: String,// 子错误码描述
    val sub_error_code: Long,   // 子错误码	0
    val description: String     // 错误码描述
)

data class Data(
    val active_time: String,    // 生成时间	2020-03-31 12:00:00
    val description: String,    // 错误码描述
    val error_code: Long,       // 错误码	0
    val list: List<ListItem>    // 实时热点词
)

data class ListItem(
    val actors: List<String>,     // 演员 [徐峥 袁泉 沈腾 吴云芳 陈奇 黄梅莹 欧丽娅 贾冰 郭京飞]
    val maoyan_id: String?, // 猫眼id：只有电影榜返回，可能为空    1250696
    val name: String,       // 片名   囧妈
    val name_en: String,    // 英文片名 Lost in Russia
    val areas: List<String>,// 地区 [中国]
    val directors: List<String>,    // 导演 [徐峥]
    val discussion_hot: Long,    // 讨论热度	789200
    val id: String,         // 影片id	6399487713065566465
    val search_hot: Long,   // 搜索热度	789200
    val influence_hot: Long,    // 影片影响力热度	789200
    val release_date: String, // 上映时间   2020-01-25
    val topic_hot: Long,    // 话题热度值	789200
    val type: Int, // 类型：1=电影 2=电视剧 3=综艺    1
    val hot: Long, // 热度    1.361e+06
    val poster: String, // 海报   https://p3-dy.bytecdn.cn/obj/compass/9ac412ae054b57f69c0967a8eb5e25c9
    val tags: List<String>, // 类型标签	[动作,犯罪,剧情]
)