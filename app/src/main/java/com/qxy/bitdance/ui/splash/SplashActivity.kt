package com.qxy.bitdance.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import java.lang.Thread.sleep


class SplashActivity : AppCompatActivity() {

    private val mScope = "user_info," +  // 抖音头像、昵称
            "following.list," +          // 关注列表
            "fans.list," +               // 粉丝列表
            "fans.check," +              // 判断是否关注抖音号能力
            "discovery.ent," +           // 获取抖音电影榜，电视剧榜以及综艺榜
            "im.share," +                // 分享图片或链接给抖音好友/群
            "video.data," +              // 查询视频数据
            "video.list," +              // 查询视频数据
            "video.search," +            // 搜索视频
            "video.search.comment," +    // 搜索视频及评论
            "data.external.item," +      // 查询视频的获赞/评论/分享数据
            "star_top_score_display," +  // 获取星图达人指数
            "trial.whitelist"            // 测试白名单

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread {
            //获取初始化数据
            runOnUiThread {
                sleep(1000)
                finish()
                sendAuth()
            }
        }.start()
    }

    private fun sendAuth(): Boolean {
        val douYinOpenApi = DouYinOpenApiFactory.create(this)
        val request = Authorization.Request()
        request.scope = mScope // 用户授权 (必选权限)
//        request.optionalScope0 = ""  // 用户授权 (可选权限) (默认不选)
//        request.optionalScope1 = ""  // 用户授权时 (可选权限) (默认选择)
        request.state = "auth_state"  // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
        request.callerLocalEntry = "com.qxy.bitdance.MainActivity"
        return douYinOpenApi.authorize(request)  // 优先使用抖音app进行授权，如果抖音app因版本或者其他原因无法授权，则使用web页授权
    }

}