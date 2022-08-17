package com.example.upload.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.SoundEffectConstants
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.core.FocusMeteringAction.FLAG_AF
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.common.base.baseui.BaseActivity
import com.example.common.base.utils.setStatusBarColor
import com.example.upload.BR
import com.example.upload.R
import com.example.upload.databinding.ActivityUploadBinding
import com.example.upload.utils.GlideEngine
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.sqrt


class UploadActivity : BaseActivity<ActivityUploadBinding,UploadViewModel>() {

    companion object {
        const val REQUEST_CODE_PERMISSIONS = 10//授权回调RequestCode

        private const val TAG = "UploadActivity"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        //需要的权限列表
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).toTypedArray()
    }

    private lateinit var cameraPreview : PreviewView
    private lateinit var cameraClick : ImageView
    private lateinit var cameraSwitch : ImageView
    private lateinit var cameraFlash : ImageView
    private lateinit var cameraClose : ImageView
    private lateinit var cameraAlbum : CircleImageView

    private lateinit var cameraSelector : CameraSelector
    private lateinit var preview: Preview//预览对象
    private lateinit var cameraProvider: ProcessCameraProvider//相机信息
    private lateinit var camera: Camera//相机对象

    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private var oldDist = 0F
    private var isOneClick = false
    private var isZoomOver = false

    override fun getLayoutId() = R.layout.activity_upload

    override fun getVariableId() = BR.uploadViewModel

    override fun initData(savedInstanceState: Bundle?) {
        setStatusBarColor(Color.BLACK)
        cameraPreview = findViewById(R.id.camera_preview)
        cameraClick = findViewById(R.id.camera_click)
        cameraSwitch = findViewById(R.id.camera_switch)
        cameraFlash = findViewById(R.id.camera_flash)
        cameraClose = findViewById(R.id.camera_close)
        cameraAlbum = findViewById(R.id.camera_album)

        initPermission()
        Glide.with(cameraAlbum).load(getAlbumFirst()).into(cameraAlbum)
        initListener()
    }

    //判断是否授权
    private fun initPermission(){
        if (allPermissionsGranted()) {
            startCamera()//已授权，启动相机
        } else {
            //未授权，请求权限
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    //设置监听
    @SuppressLint("ClickableViewAccessibility", "RestrictedApi")
    private fun initListener(){
        //关闭按钮
        cameraClose.setOnClickListener {
            finish()
        }
        //拍照按钮
        cameraClick.setOnClickListener {
            takePhoto()
        }
        //前后置摄像头切换按钮
        cameraSwitch.setOnClickListener {
            cameraSelector = if (cameraSelector.lensFacing == CameraSelector.LENS_FACING_BACK){
                CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                    .build()
            }else{
                CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
            }
            //每次更改相机都需要重新绑定
            bindPreview()
        }

        cameraPreview.setOnTouchListener { _, motionEvent ->
            //当最后一根手指离开时，判断是缩放动作完成还是聚焦动作完成
            if (motionEvent.action == MotionEvent.ACTION_UP){
                if (isOneClick){
                    val point = cameraPreview.meteringPointFactory
                        .createPoint(motionEvent.x, motionEvent.y)
                    val action = FocusMeteringAction.Builder(point,FLAG_AF)
                        .setAutoCancelDuration(3,TimeUnit.SECONDS)
                        .build()
                    showTapView(motionEvent.rawX.toInt(), motionEvent.rawY.toInt())
                    camera.cameraControl.startFocusAndMetering(action)
                }
                //重置缩放和聚焦判断
                isOneClick = false
                isZoomOver = false
            }
            //当聚焦点数量为1时，将聚焦置为true
            if (motionEvent.pointerCount == 1){
                if(!isZoomOver) isOneClick = true
            }else{
                //当聚焦点数量大于1时，将聚焦置为false，且开始进行镜头的缩放
                isOneClick = false
                when (motionEvent.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_POINTER_DOWN -> oldDist = getFingerSpacing(motionEvent)
                    MotionEvent.ACTION_MOVE -> {
                        val newDist: Float = getFingerSpacing(motionEvent)
                        if (newDist > oldDist) handleZoom(true)
                        else if (newDist < oldDist) handleZoom(false)
                        oldDist = newDist
                        isZoomOver = true
                    }
                }
            }
            true
        }
        //闪光灯模式切换按钮
        cameraFlash.setOnClickListener {
            when(imageCapture?.flashMode){
                ImageCapture.FLASH_MODE_AUTO ->{
                    imageCapture!!.flashMode = ImageCapture.FLASH_MODE_ON
                    cameraFlash.setImageResource(R.drawable.ic_camera_flash_on)
                }
                ImageCapture.FLASH_MODE_OFF ->{
                    imageCapture!!.flashMode = ImageCapture.FLASH_MODE_AUTO
                    cameraFlash.setImageResource(R.drawable.ic_camera_flash_auto)
                }
                ImageCapture.FLASH_MODE_ON ->{
                    imageCapture!!.flashMode = ImageCapture.FLASH_MODE_OFF
                    cameraFlash.setImageResource(R.drawable.ic_camera_flash_close)
                }
                else->{}
            }
        }
        //点击跳转到相册以及相机拍摄完成后显示预览图
        cameraAlbum.setOnClickListener {
            PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .isDisplayCamera(false)
                .setImageEngine(GlideEngine.createGlideEngine())
                .setImageSpanCount(3)
                .setSelectionMode(SelectModeConfig.MULTIPLE)
                .setMaxSelectNum(9)
                .isPreviewFullScreenMode(true)
                .isPreviewZoomEffect(true)
                .isPreviewImage(true)
                .isEmptyResultReturn(true)
                .forResult(PictureConfig.CHOOSE_REQUEST)
        }
    }

    //启动相机
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // 将相机的生命周期绑定到应用进程
            cameraProvider = cameraProviderFuture.get()
            //预览配置
            preview = Preview.Builder().build()
                .also {
                    //提供previewView预览控件
                    it.setSurfaceProvider(cameraPreview.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().build()
            imageCapture?.flashMode = ImageCapture.FLASH_MODE_OFF

            cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK) //后置
                .build()

            try {
                bindPreview()
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    //绑定Preview
    private fun bindPreview(){
        cameraProvider.unbindAll()//先解绑所有用例
        camera = cameraProvider.bindToLifecycle(
            this,
            cameraSelector,
            imageCapture,
            preview)
        //绑定用例
    }

    //拍摄照片
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.CHINA)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults){
                    Glide.with(cameraAlbum).load(output.savedUri).into(cameraAlbum)
                    //拍照完成后显示在相机预览图中
                }
            }
        )
    }

    //判断权限是否获取成功
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    //显示聚焦图标
    private fun showTapView(x: Int, y: Int) {
        val size = resources.getDimension(R.dimen.DP70).toInt()
        val popupWindow = PopupWindow(size, size)
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.ic_focus)
        popupWindow.animationStyle = R.style.camera_focus_anim_style
        popupWindow.contentView = imageView
        popupWindow.showAsDropDown(cameraPreview, x-size/2, y)
        cameraPreview.postDelayed({ popupWindow.dismiss() }, 1000)
        cameraPreview.playSoundEffect(SoundEffectConstants.CLICK)
    }

    //获得相册中第一张图片
    private fun getAlbumFirst() : String{
        val uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.RELATIVE_PATH
            )
        } else {
            arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA
            )
        }
        contentResolver.query(uriExternal,projection,null,null,null)
            ?.use { cursor ->
                if(cursor.moveToLast()) {
                    val columnIndexID = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                    val imageId = cursor.getLong(columnIndexID)
                    val uriImage = ContentUris.withAppendedId(uriExternal, imageId)
                    return uriImage.toString()
                }
        }
        return ""
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "授权失败，请到设置页中对应用进行相应的授权",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    //计算手指的间距
    private fun getFingerSpacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt((x * x + y * y).toDouble()).toFloat()
    }

    //根据双指移动时进行缩放
    private fun handleZoom(isZoomIn : Boolean) {
        if (camera.cameraInfo.zoomState.value != null){
            val currentZoom = camera.cameraInfo.zoomState.value!!.zoomRatio
            val maxZoom = camera.cameraInfo.zoomState.value!!.maxZoomRatio
            if (isZoomIn && currentZoom < maxZoom) camera.cameraControl.setZoomRatio(currentZoom + 0.08F)
            else if (currentZoom > 0) camera.cameraControl.setZoomRatio(currentZoom - 0.08F)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK){
//            if (requestCode == PictureConfig.CHOOSE_REQUEST){
//                val selectPictureList = PictureSelector.obtainSelectorList(data)
//                getViewModel().uploadPictureSize = selectPictureList.size
//                for (picture in selectPictureList) getViewModel().uploadPicture(picture.realPath)
//            }
//        }
    }
}