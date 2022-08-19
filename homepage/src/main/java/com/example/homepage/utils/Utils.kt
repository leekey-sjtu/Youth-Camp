package com.example.homepage.utils

import android.util.Log

fun myLog(msg : String) {
    Log.e("wdw", msg)
}

fun getSkyCondition(skycon: String): String {
    when (skycon) {
        "CLEAR_DAY" -> return "晴"
        "CLEAR_NIGHT" -> return "晴"
        "PARTLY_CLOUDY_DAY" -> return "多云"
        "PARTLY_CLOUDY_NIGHT" -> return "多云"
        "CLOUDY" -> return "阴"
        "LIGHT_HAZE" -> return "轻度雾霾"
        "MODERATE_HAZE" -> return "中度雾霾"
        "HEAVY_HAZE" -> return "重度雾霾"
        "LIGHT_RAIN" -> return "小雨"
        "MODERATE_RAIN" -> return "中雨"
        "HEAVY_RAIN" -> return "大雨"
        "STORM_RAIN" -> return "暴雨"
        "FOG" -> return "雾"
        "LIGHT_SNOW" -> return "小雪"
        "MODERATE_SNOW" -> return "中雪"
        "HEAVY_SNOW" -> return "大雪"
        "STORM_SNOW" -> return "暴雪"
        "DUST" -> return "浮尘"
        "SAND" -> return "沙尘"
        "WIND" -> return "大风"
    }
    return ""
}