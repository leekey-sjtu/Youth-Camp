package com.qxy.bitdance

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bytedance.sdk.open.aweme.CommonConstants
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler
import com.bytedance.sdk.open.aweme.common.model.BaseReq
import com.bytedance.sdk.open.aweme.common.model.BaseResp
import com.bytedance.sdk.open.aweme.share.Share
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi

/**
 * test
 * 接受授权返回结果的activity
 */
class DouYinEntryActivity : Activity(), IApiEventHandler {

    private lateinit var douYinOpenApi: DouYinOpenApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        douYinOpenApi = DouYinOpenApiFactory.create(this)
        douYinOpenApi.handleIntent(intent, this)

        Log.d("wdw", "DouYinEntryActivity")
    }

    override fun onReq(req: BaseReq) {

    }

    override fun onResp(resp: BaseResp) {
        if (resp.type == CommonConstants.ModeType.SEND_AUTH_RESPONSE) {
            val response = resp as Authorization.Response
            if (resp.isSuccess()) {
                Toast.makeText(this, "授权成功，获得权限：" + response.grantedPermissions, Toast.LENGTH_LONG).show()
//                val intent = Intent(this,MainActivity::class.java)
//                startActivity(intent)
                // response.authCode
            } else {
                Toast.makeText(this, "授权失败" + response.grantedPermissions, Toast.LENGTH_LONG).show()
            }
            finish()
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
}