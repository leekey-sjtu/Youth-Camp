package com.example.common.base.service

import android.content.Context

object SharedPreferencesService {
    private const val PREF_NAME = "pref_name"

    fun getOpenId(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getString("open_id", "")!!
    }

    fun saveOpenId(context: Context, openId: String){
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putString("open_id", openId).apply()
    }
}