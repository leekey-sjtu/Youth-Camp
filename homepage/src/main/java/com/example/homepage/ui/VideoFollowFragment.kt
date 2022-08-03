package com.example.homepage.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homepage.R
import com.example.homepage.utils.setAndroidNativeLightStatusBar
import com.example.homepage.utils.setStatusBarColor

class VideoFollowFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        return inflater.inflate(R.layout.fragment_video_follow, container, false)
    }

    override fun getUserVisibleHint(): Boolean {
        return super.getUserVisibleHint()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        Log.d("wdw", "follow setUserVisibleHint")
        if (isVisibleToUser) {
            requireActivity().setStatusBarColor(Color.TRANSPARENT) // TODO: 状态栏反色
            requireActivity().setAndroidNativeLightStatusBar()
        }
    }

}