package com.example.upload.service

import com.example.upload.bean.ReleaseBean
import com.example.upload.bean.UploadBean
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UploadService {

    @Multipart
    @POST("image/upload/")
    suspend fun uploadPicture(
        @Header("access-token") accessToken : String,
        @Query("open_id") openId : String,
        @Part picture : MultipartBody.Part) : UploadBean

    @POST("image_text/create/")
    suspend fun releasePicture(
        @Header("access-token") accessToken : String,
        @Query("open_id") openId : String,
        @Body body: RequestBody) : ReleaseBean

}