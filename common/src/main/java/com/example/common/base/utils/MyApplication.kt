package com.example.common.base.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.DouYinOpenConfig
import com.danikula.videocache.HttpProxyCacheServer
import com.example.common.base.service.TokenProService
import kotlinx.coroutines.runBlocking

class MyApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        var proxy: HttpProxyCacheServer? = null

        fun getProxy(context: Context): HttpProxyCacheServer {
            val app: MyApplication = context.applicationContext as MyApplication
            return proxy ?: app.newProxy()
        }
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        runBlocking {
            val clientKey = TokenProService.getClientKey()  // 服务器远程获取client_key
            DouYinOpenApiFactory.init(DouYinOpenConfig(clientKey))
        }
    }

    private fun newProxy(): HttpProxyCacheServer {
        return HttpProxyCacheServer.Builder(this)
            .maxCacheFilesCount(20)  // 最大缓存数量
            .maxCacheSize(512 * 1024 * 1024)  // 最大缓存大小 512MB
            .build()
    }

}