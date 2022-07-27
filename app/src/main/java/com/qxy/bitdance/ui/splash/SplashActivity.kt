package com.qxy.bitdance.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qxy.bitdance.MainActivity
import java.lang.Thread.sleep


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread {
            //获取初始化数据
            runOnUiThread {
                sleep(1000)
                val intent = Intent(this,MainActivity::class.java)
                finish()
                startActivity(intent)
            }
        }.start()

    }
}