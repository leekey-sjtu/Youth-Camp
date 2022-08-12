package com.example.upload.service

import com.example.common.base.constants.TokenConstants
import com.example.common.base.network.RetrofitClient
import com.example.upload.bean.Release
import com.example.upload.bean.ReleaseBean
import com.example.upload.bean.UploadBean
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object UploadNetWork {

    private val uploadService = RetrofitClient.retrofit.create(UploadService::class.java)

    suspend fun uploadPicture(picturePath: String) : UploadBean{
        val file = File(picturePath)
        val imageBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(),file)
        val imagePart = MultipartBody.Part.createFormData("image=@",file.name,imageBody)
        return uploadService.uploadPicture(
            TokenConstants.ACCESS_TOKEN,
            TokenConstants.OPEN_ID,
            imagePart)
    }

    suspend fun releasePicture(release: Release) : ReleaseBean{
        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            Gson().toJson(release))
        return uploadService.releasePicture(
            TokenConstants.ACCESS_TOKEN,
            TokenConstants.OPEN_ID,
            requestBody)
    }

}