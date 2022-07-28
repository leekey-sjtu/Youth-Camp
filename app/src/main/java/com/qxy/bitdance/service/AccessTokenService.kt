package com.qxy.bitdance.service

import com.qxy.bitdance.bean.AccessTokenResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AccessTokenService {
    @GET("/oauth/access_token")
    fun getAccessToken(@Query("auth_code") auth_code: String): Call<AccessTokenResponse>
}