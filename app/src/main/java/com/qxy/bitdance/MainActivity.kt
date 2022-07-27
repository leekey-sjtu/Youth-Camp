package com.qxy.bitdance

import android.os.Bundle
import android.widget.Button
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi
import com.qxy.bitdance.baseui.BaseActivity
import com.qxy.bitdance.databinding.ActivityMainBinding
import com.qxy.bitdance.test.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(){

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

        findViewById<Button>(R.id.btn_douyin).setOnClickListener {
            sendAuth()
        }
    }

    private fun sendAuth(): Boolean {
//        private lateinit var douYinOpenApi: DouYinOpenApi
        val douYinOpenApi = DouYinOpenApiFactory.create(this)
        val request = Authorization.Request()
        request.scope = "user_info,trial.whitelist"  // 用户授权时必选权限
        request.optionalScope0 = "mobile"  // 用户授权时可选权限（默认不选）
        request.optionalScope1 = "friend_relation, message"  // 用户授权时可选权限（默认选择）
        request.state = "ww"  // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
        return douYinOpenApi.authorize(request)  // 优先使用抖音app进行授权，如果抖音app因版本或者其他原因无法授权，则使用web页授权
    }
}