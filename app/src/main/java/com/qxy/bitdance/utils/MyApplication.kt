package com.qxy.bitdance.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.DouYinOpenConfig

class MyApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        val clientKey = "awf251n1psyxh65f" // client key
//        val clientKey = "awsxdh3k1fiojgnu"  // test client key
        DouYinOpenApiFactory.init(DouYinOpenConfig(clientKey))
    }
}