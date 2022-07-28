package com.qxy.bitdance.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.qxy.bitdance.MainActivity
import java.lang.Thread.sleep


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread {
            //获取初始化数据
            runOnUiThread {
                sleep(1000)
//                val intent = Intent(this,MainActivity::class.java)
                finish()
//                startActivity(intent)
                sendAuth()
            }
        }.start()

    }

    private fun sendAuth(): Boolean {
        val douYinOpenApi = DouYinOpenApiFactory.create(this)
        val request = Authorization.Request()
        request.scope = "user_info,trial.whitelist"  // 用户授权时必选权限
//        request.optionalScope0 = "mobile"  // 用户授权时可选权限（默认不选）
//        request.optionalScope1 = "friend_relation, message"  // 用户授权时可选权限（默认选择）
        request.state = "auth_state"  // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
        request.callerLocalEntry = "com.qxy.bitdance.MainActivity"
        return douYinOpenApi.authorize(request)  // 优先使用抖音app进行授权，如果抖音app因版本或者其他原因无法授权，则使用web页授权
    }

}