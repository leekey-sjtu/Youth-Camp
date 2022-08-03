package com.example.hotlist.utils

object HotListUtil {
    fun formatDate(date: String): String{
        date.replace("-", ".")
        return date.substring(2, date.length)
    }
}