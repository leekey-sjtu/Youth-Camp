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
import com.example.common.base.bean.AccessTokenResponse
import com.example.common.base.bean.HotListResponse
import com.example.common.base.bean.HotListTokenResponse
import com.qxy.bitdance.databinding.ActivityMainBinding
import com.example.common.base.service.AccessTokenService
import com.example.common.base.service.HotListService
import com.qxy.bitdance.test.MainViewModel
import com.example.homepage.ui.homepage.VideoFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
//        getViewModel().getCatList()
        getViewModel().closeLoading()

        douYinOpenApi = DouYinOpenApiFactory.create(this)
        douYinOpenApi.handleIntent(intent, this)

        supportFragmentManager.beginTransaction().add(R.id.lay_fragment_container,
            com.example.homepage.ui.homepage.VideoFragment()).commit()  //初始化首个fragment
    }

    override fun onReq(req: BaseReq) {

    }

    override fun onResp(resp: BaseResp) {
        if (resp.type == CommonConstants.ModeType.SEND_AUTH_RESPONSE) {
            val response = resp as Authorization.Response
            if (resp.isSuccess()) {
                Toast.makeText(this, "授权成功，获得权限：" + response.grantedPermissions, Toast.LENGTH_LONG).show()
                Log.d("wdw", "授权成功, 获得权限：" + response.grantedPermissions)
                val authCode = response.authCode
                val state = response.state
                Log.d("wdw", "authCode = $authCode // 临时票据code\nstate = $state // 请求,回调状态")
                getAccessToken(authCode)
            } else {
                Toast.makeText(this, "授权失败" + response.grantedPermissions, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onErrorIntent(intent: Intent) {
        Toast.makeText(this, "Intent出错", Toast.LENGTH_LONG).show() // 错误数据
    }

    private fun getAccessToken(authCode: String) {
        RetrofitClient.retrofit
            .create(AccessTokenService::class.java)
            .getAccessToken(
                "33624d75890346aa6c45a3c929780135",
                authCode,
                "authorization_code",
                "awf251n1psyxh65f"
            )
            .enqueue(object : Callback<AccessTokenResponse> {
                override fun onResponse(call: Call<AccessTokenResponse>, response: Response<AccessTokenResponse>, ) {
                    Log.d("wdw", "get access token success")
                    val body = response.body()!!
                    val access_token = body.data.access_token  // 接口调用凭证access_token
                    val expires_in = body.data.expires_in  // access_token超时时间，单位（秒)
                    val refresh_token = body.data.refresh_token  // 用户刷新access_token
                    val refresh_expires_in = body.data.refresh_expires_in  // refresh_token凭证超时时间，单位（秒)
                    val open_id = body.data.open_id  // 授权用户唯一标识
                    val scope = body.data.scope  // 用户授权的作用域Scope，使用逗号,分隔，开放平台几乎几乎每个接口都需要特定的Scope
                    val error_code = body.data.error_code  // 错误码, 0表示成功
                    val description = body.data.description  // 错误码描述
                    Log.d("wdw",
                        "access_token = $access_token\n" +
                            "expires_in = $expires_in // access_token过期时间\n" +
                            "refresh_token = $refresh_token\n" +
                            "refresh_expires_in = $refresh_expires_in // refresh_token过期时间\n" +
                            "open_id = $open_id // 授权用户唯一标识\n" +
                            "scope = $scope\n" +
                            "error_code = $error_code\n" +
                            "description = $description"
                    )
                    getHotListToken()
                }

                override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                    Log.d("wdw", "get access token failed -> $t")
                }
            })
    }

    private fun getHotListToken() {
        RetrofitClient.retrofit
            .create(HotListService::class.java)
            .getClientToken(  // TODO(需要获取 client_key / client_secret)
                "awf251n1psyxh65f",
                "33624d75890346aa6c45a3c929780135",
                "client_credential"
            )
            .enqueue(object : Callback<HotListTokenResponse> {
                override fun onResponse(call: Call<HotListTokenResponse>, response: Response<HotListTokenResponse>, ) {
                    Log.d("wdw", "get hot list token success")
                    val body = response.body()
                    if (body != null) {
                        val clientAccessToken = body.data.access_token
                        val errorCode = body.data.error_code
                        Log.d("wdw", "error_code = $errorCode\nclientAccessToken = $clientAccessToken")
                        getHotList(clientAccessToken)
                    }
                }

                override fun onFailure(call: Call<HotListTokenResponse>, t: Throwable) {
                    Log.d("wdw", "get hot list token failed -> $t")
                }

            })
    }

    private fun getHotList(clientAccessToken: String) {
        RetrofitClient.retrofit
            .create(HotListService::class.java)
            .getHotList(
                clientAccessToken,
                1,
                0
            )
            .enqueue(object : Callback<HotListResponse> {
                override fun onResponse(call: Call<HotListResponse>, response: Response<HotListResponse>, ) {
                    Log.d("wdw", "get hot list success")
                    val body = response.body()
                    if (body != null) {
                        val error_code = body.data.error_code
                        Log.d("wdw", "error_code = $error_code\n")
                    }
                }

                override fun onFailure(call: Call<HotListResponse>, t: Throwable) {
                    Log.d("wdw", "get hot list failed -> $t\n")
                }

            })
    }

}