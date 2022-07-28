package com.qxy.bitdance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bytedance.sdk.open.aweme.CommonConstants
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler
import com.bytedance.sdk.open.aweme.common.model.BaseReq
import com.bytedance.sdk.open.aweme.common.model.BaseResp
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi
import com.example.common.base.baseui.BaseActivity
import com.example.common.base.network.RetrofitClient
import com.qxy.bitdance.bean.AccessTokenResponse
import com.qxy.bitdance.databinding.ActivityMainBinding
import com.qxy.bitdance.service.AccessTokenService
import com.qxy.bitdance.test.MainViewModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), IApiEventHandler {

    private lateinit var douYinOpenApi: DouYinOpenApi

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getVariableId(): Int {
        return BR.mainViewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        getViewModel().showLoading()
        getViewModel().catListData.observe(this) {
            println("MainActivity $it")
        }
        getViewModel().getCatList()
        getViewModel().closeLoading()

        douYinOpenApi = DouYinOpenApiFactory.create(this)
        douYinOpenApi.handleIntent(intent, this)

    }

    override fun onReq(req: BaseReq) {

    }

    override fun onResp(resp: BaseResp) {
        if (resp.type == CommonConstants.ModeType.SEND_AUTH_RESPONSE) {
            val response = resp as Authorization.Response
            if (resp.isSuccess()) {
                Toast.makeText(this, "授权成功，获得权限：" + response.grantedPermissions, Toast.LENGTH_LONG).show()
                val authCode = response.authCode
                val state = response.state
                Log.d("wdw", "authCode = $authCode\nstate = $state")
                getAccessToken(authCode)
            } else {
                Toast.makeText(this, "授权失败" + response.grantedPermissions, Toast.LENGTH_LONG).show()
            }
//            finish()
        }

//        else if (resp.type == CommonConstants.ModeType.SHARE_CONTENT_TO_TT_RESP) {
//            val response = resp as Share.Response
//            Toast.makeText(this,
//                " code：" + response.errorCode + " 文案：" + response.errorMsg,
//                Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
    }

    override fun onErrorIntent(intent: Intent) {
        // 错误数据
        Toast.makeText(this, "Intent出错", Toast.LENGTH_LONG).show()
    }

    private fun getRetrofit(): Retrofit {

        val baseUrl = "https://open.douyin.com/"
        val client = OkHttpClient.Builder()
            .connectTimeout(15000, TimeUnit.MILLISECONDS)  //预留足够的时间连接服务器
            .readTimeout(15000, TimeUnit.MILLISECONDS)  //预留足够的时间处理数据，否则偶尔出现超时java.net.SocketTimeoutException: timeout
            .build()
        val factory = GsonConverterFactory.create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(factory)
            .build()

    }

    private fun getAccessToken(authCode: String) {
        getRetrofit()
            .create(AccessTokenService::class.java)
            .getAccessToken(authCode)
            .enqueue(object : Callback<AccessTokenResponse> {
                override fun onResponse(call: Call<AccessTokenResponse>, response: Response<AccessTokenResponse>, ) {
                    Log.d("wdw", "get access token success")

                    Log.d("wdw", "${response.body()}")
//                    return response.body()
                }

                override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                    Log.d("wdw", "get access token failed -> $t")
                }

            })
    }

}