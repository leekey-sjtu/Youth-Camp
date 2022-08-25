package com.example.upload.service

import android.util.Log
import com.example.upload.bean.Release
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UploadRepository {

    private companion object {
        const val TAG = "UploadRepository"
    }

    fun uploadPicture(picturePath : String) = flow {
        val uploadResponse = UploadNetWork.uploadPicture(picturePath)
        if (uploadResponse.data.error_code == 0) emit(uploadResponse.data.image.image_id)
        else Log.v(TAG,"uploadPicture请求错误，错误码：${uploadResponse.data.description}")
    }.flowOn(Dispatchers.IO)

    fun releasePicture(release: Release) = flow {
        val releaseResponse = UploadNetWork.releasePicture(release)
        if (releaseResponse.data.error_code == 0) emit(releaseResponse.data.error_code)
        else Log.v(TAG,"releasePicture请求错误，错误码：${releaseResponse.data.error_code}")
    }.flowOn(Dispatchers.IO)

}