package com.example.homepage.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.common.base.baseui.BaseActivity
import com.example.homepage.BR
import com.example.homepage.R
import com.example.homepage.databinding.ActivityNewsDetailBinding
import com.example.homepage.viewmodel.NewsDetailViewModel

class NewsDetailActivity : BaseActivity<ActivityNewsDetailBinding, NewsDetailViewModel>() {

    private lateinit var webView: WebView

    override fun getLayoutId() = R.layout.activity_news_detail

    override fun getVariableId() = BR.newsDetailViewModel

    override fun initData(savedInstanceState: Bundle?) {
        // 设置 ViewDataBinding
        setViewDataBinding()

        // 设置新闻 webView
        val newsUrl = intent.getStringExtra("newsUrl")
        setNewsDetail(newsUrl)
    }

    private fun setViewDataBinding() {
        webView = getDataBinding().webView
    }

    private fun setNewsDetail(newsUrl: String?) {
        newsUrl?.let { webView.loadUrl(it) }
        webView.webViewClient = WebViewClient()
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true  //启用js
        webSettings.blockNetworkImage = false  //显示网络图片
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW  //设置http和https混用
        webSettings.standardFontFamily = "Time News Roman"  //设置 WebView 的字体，默认字体为 "sans-serif"
        webSettings.defaultFontSize = 30  //设置 WebView 字体的大小，默认大小为 16
        webSettings.minimumFontSize = 12  //设置 WebView 支持的最小字体大小，默认为 8
    }

    override fun onResume() {
        super.onResume()
        window.statusBarColor = Color.TRANSPARENT //设置状态栏颜色
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //实现状态栏字体黑色
    }

}