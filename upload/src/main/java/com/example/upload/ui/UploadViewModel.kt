package com.example.upload.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseViewModel
import com.example.upload.bean.Release
import com.example.upload.service.UploadRepository
import kotlinx.coroutines.launch

class UploadViewModel : BaseViewModel() {

    private val uploadRepository = UploadRepository()
    var uploadPictureSize = 0
    private val imageIdList = mutableListOf<String>()

    fun uploadPicture(picturePath : String){
        viewModelScope.launch{
            uploadRepository.uploadPicture(picturePath).collect{
                imageIdList.add(it)
                if (uploadPictureSize > 0) uploadPictureSize--
                if (uploadPictureSize == 0) Log.v("UploadViewModel","上传成功")
            }
        }
    }

}