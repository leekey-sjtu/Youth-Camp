package com.example.homepage

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.danikula.videocache.HttpProxyCacheServer


class MainApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        var proxy: HttpProxyCacheServer? = null

        fun getProxy(context: Context): HttpProxyCacheServer {
            val app: MainApplication = context.applicationContext as MainApplication
            return proxy ?: app.newProxy()
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    private fun newProxy(): HttpProxyCacheServer {
        return HttpProxyCacheServer.Builder(this)
//            .cacheDirectory()  // 缓存目录
            .maxCacheFilesCount(20)  // 最大缓存数量
            .maxCacheSize(512 * 1024 * 1024)  // 最大缓存大小 512MB
            .build()
    }
}