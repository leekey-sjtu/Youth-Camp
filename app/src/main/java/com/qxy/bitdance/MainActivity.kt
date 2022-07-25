package com.qxy.bitdance

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.qxy.bitdance.test.CatListViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mVmCatList: CatListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mVmCatList = ViewModelProvider(this)[CatListViewModel::class.java]
        mVmCatList.viewModelScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mVmCatList.mCatListStateFlow.collect{
                    Toast.makeText(this@MainActivity, "我我我$it", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "onCreate: 我我 我$it" )
                }
            }
        }

    }
}