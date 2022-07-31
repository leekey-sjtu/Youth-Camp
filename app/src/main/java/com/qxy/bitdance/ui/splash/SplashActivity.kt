package com.qxy.bitdance.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.example.common.base.bean.HotListTokenResponse
import com.example.common.base.constants.TokenConstants
import com.example.common.base.network.RetrofitClient
import com.example.common.base.service.HotListService
import com.example.common.base.service.SharedPreferencesService
import com.example.common.base.service.TokenProService
import com.example.personal.ui.friendList.FriendActivity
import com.qxy.bitdance.MainActivity
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

        getClientToken()

        Thread {
            val openId = SharedPreferencesService.getOpenId(this)
            if (openId == "") {
                runOnUiThread {
                    sendAuth()                    // 本地没有open_id， 则打开抖音授权
                }
            } else {
                TokenConstants.OPEN_ID = openId   // 利用本地open_id， 远程获取对应的token
                runBlocking {
                    TokenConstants.CLIENT_KEY = TokenProService.getClientKey()  // 初始化TokenConstants
                    TokenConstants.CLIENT_SECRET = TokenProService.getClientSecret()
                    TokenConstants.ACCESS_TOKEN = TokenProService.getAccessToken(openId)   // TODO: 可能要判断是否过期
                    TokenConstants.REFRESH_TOKEN = TokenProService.getRefreshToken(openId)
                }
                val intent = Intent(this, FriendActivity::class.java)
                startActivity(intent)
            }
            sleep(1000)
            //获取初始化数据
            finish()
        }.start()

    }


    // 跳转抖音授权
    private fun sendAuth(): Boolean {
        val douYinOpenApi = DouYinOpenApiFactory.create(this)
        val request = Authorization.Request()
        request.scope = mScope                                      // 用户授权 (必选权限)
        request.state = "auth_state"                                // 保持请求和回调的状态，授权请求后原样带回给第三方
        request.callerLocalEntry = "com.qxy.bitdance.MainActivity"  // 授权后返回MainActivity
        return douYinOpenApi.authorize(request)                     // 优先使用抖音app进行授权，如果抖音app因版本或者其他原因无法授权，则使用web页授权
    }


    // 获取client_token
    private fun getClientToken() {
        RetrofitClient.retrofit
            .create(HotListService::class.java)
            .getClientToken(
                runBlocking { TokenProService.getClientKey() },
                runBlocking { TokenProService.getClientSecret() },
                "client_credential"
            )
            .enqueue(object : Callback<HotListTokenResponse> {
                override fun onResponse(call: Call<HotListTokenResponse>, response: Response<HotListTokenResponse>, ) {
                    Log.d("wdw", "get client_token success")
                    TokenConstants.CLIENT_TOKEN = response.body()!!.data.access_token
                }

                override fun onFailure(call: Call<HotListTokenResponse>, t: Throwable) {
                    Log.d("wdw", "get client_token failed -> $t")
                }
            })
    }


}