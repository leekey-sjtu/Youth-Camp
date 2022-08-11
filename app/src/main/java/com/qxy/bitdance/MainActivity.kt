package com.qxy.bitdance

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bytedance.sdk.open.aweme.CommonConstants
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler
import com.bytedance.sdk.open.aweme.common.model.BaseReq
import com.bytedance.sdk.open.aweme.common.model.BaseResp
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi
import com.example.common.base.baseui.BaseActivity
import com.example.common.base.bean.AccessTokenResponse
import com.example.common.base.constants.TokenConstants
import com.example.common.base.network.RetrofitClient
import com.example.common.base.service.AccessTokenService
import com.example.common.base.service.SharedPreferencesService
import com.example.common.base.service.TokenProService
import com.example.homepage.ui.HomePageFragment
import com.example.homepage.utils.myLog
import com.example.hotlist.ui.hotlist.HotListTabFragment
import com.google.android.material.tabs.TabLayout
import com.qxy.bitdance.databinding.ActivityMainBinding
import com.qxy.bitdance.test.MainViewModel
import com.qxy.bitdance.test.TestFragment
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), IApiEventHandler {

    private lateinit var douYinOpenApi: DouYinOpenApi
    private val tabLayout: TabLayout by lazy { findViewById(R.id.tabLayout) }
    private val homePageFragment = HomePageFragment()
    private val hotListTabFragment =  HotListTabFragment()
    private val testFragment2 =  TestFragment("发布")
    private val testFragment3 =  TestFragment("消息")
    private val testFragment4 =  TestFragment("我")
    private lateinit var currentFragment : Fragment

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getVariableId(): Int {
        return BR.mainViewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        getViewModel().showLoading()
        getViewModel().catListData.observe(this) {
            println("MainActivity $it")
        }
        getViewModel().closeLoading()

        douYinOpenApi = DouYinOpenApiFactory.create(this)
        douYinOpenApi.handleIntent(intent, this)

        // 初始化首个fragment
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, homePageFragment).commit()

        // 记录当前fragment为homePageFragment
        currentFragment = homePageFragment

        // 设置底部导航栏View
        setBottomNavigationTabCustomView()

        // 设置底部导航栏tabLayout点击事件
        setBottomNavigationTabSelect()
    }


    // 设置底部导航栏View
    private fun setBottomNavigationTabCustomView() {
        tabLayout.getTabAt(0)?.customView = layoutInflater.inflate(R.layout.bottom_navigation_tab, null)
        tabLayout.getTabAt(0)?.customView?.findViewById<TextView>(R.id.textView)?.text = "首页"
        tabLayout.getTabAt(0)?.customView?.findViewById<TextView>(R.id.textView)?.setTextColor(Color.BLACK)
        tabLayout.getTabAt(1)?.customView = layoutInflater.inflate(R.layout.bottom_navigation_tab, null)
        tabLayout.getTabAt(1)?.customView?.findViewById<TextView>(R.id.textView)?.text = "榜单"
        tabLayout.getTabAt(2)?.customView = layoutInflater.inflate(R.layout.bottom_navigation_tab_middle, null)
        tabLayout.getTabAt(2)?.customView?.findViewById<ImageView>(R.id.imgView)?.setImageResource(R.drawable.ic_bottom_navigation_tab)
        tabLayout.getTabAt(3)?.customView = layoutInflater.inflate(R.layout.bottom_navigation_tab, null)
        tabLayout.getTabAt(3)?.customView?.findViewById<TextView>(R.id.textView)?.text = "消息"
        tabLayout.getTabAt(4)?.customView = layoutInflater.inflate(R.layout.bottom_navigation_tab, null)
        tabLayout.getTabAt(4)?.customView?.findViewById<TextView>(R.id.textView)?.text = "我"
    }


    // 设置底部导航栏tabLayout点击事件
    private fun setBottomNavigationTabSelect() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> switchFragment(homePageFragment)
                    1 -> switchFragment(hotListTabFragment)
                    2 -> switchFragment(testFragment2)
                    3 -> switchFragment(testFragment3)
                    4 -> switchFragment(testFragment4)
                }
                if (tab.position != 2) {
                    val textView = tab.customView!!.findViewById<TextView>(R.id.textView)
                    textView.setTextColor(Color.BLACK)
                    val animator1 = ObjectAnimator.ofFloat(textView, "scaleX", 1f, 1.2f, 1f)
                    val animator2 = ObjectAnimator.ofFloat(textView, "scaleY", 1f, 1.2f, 1f)
                    val animSet = AnimatorSet()
                    animSet.duration = 200
                    animSet.play(animator1).with(animator2)
                    animSet.start()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab!!.position != 2) {
                    val textView = tab.customView!!.findViewById<TextView>(R.id.textView)
                    textView.setTextColor(Color.GRAY)
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }


    // 切换fragment
    private fun switchFragment(newFragment: Fragment) {
        if (newFragment.isAdded) {                          // 判断fragment是否已添加
            supportFragmentManager.beginTransaction()
                .hide(currentFragment)                      // hide当前fragment
                .show(newFragment)                          // 显示新的fragment
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .hide(currentFragment)                      // hide当前fragment
                .add(R.id.fragment_container, newFragment)  // 先add，再显示新的fragment
                .commit()
        }
        currentFragment = newFragment                       //记录currentFragment
    }


    // 获取auth_code  // 抖音api
    override fun onReq(req: BaseReq) {}
    override fun onErrorIntent(intent: Intent?) {}
    override fun onResp(resp: BaseResp) {
        if (resp.type == CommonConstants.ModeType.SEND_AUTH_RESPONSE) {
            val response = resp as Authorization.Response
            if (resp.isSuccess()) {
                Toast.makeText(this, "抖音授权成功！", Toast.LENGTH_SHORT).show()
                getAccessToken(response.authCode)
            } else {
                Toast.makeText(this, "抖音授权失败！", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // 获取access_token
    private fun getAccessToken(authCode: String) {
        RetrofitClient.retrofit
            .create(AccessTokenService::class.java)
            .getAccessToken(
                runBlocking { TokenProService.getClientSecret() },
                authCode,
                "authorization_code",
                runBlocking { TokenProService.getClientKey() },
            )
            .enqueue(object : Callback<AccessTokenResponse> {
                override fun onResponse(call: Call<AccessTokenResponse>, response: Response<AccessTokenResponse>, ) {
                    myLog("get access_token success")
                    val data = response.body()!!.data
                    val openId = data.open_id
                    val accessToken = data.access_token
                    val refreshToken = data.refresh_token
                    runBlocking {
                        SharedPreferencesService.saveOpenId(context = baseContext, openId)        // 本地储存open_id
                        TokenProService.saveAccessToken(accessToken, openId)                      // 远程储存access_token
                        TokenProService.saveRefreshToken(refreshToken, openId)                    // 远程储存refresh_token
                        TokenConstants.ACCESS_TOKEN = accessToken
                        TokenConstants.REFRESH_TOKEN = refreshToken                               // 初始化TokenConstants
                        TokenConstants.CLIENT_KEY = TokenProService.getClientKey()
                        TokenConstants.CLIENT_SECRET = TokenProService.getClientSecret()
                    }
                }
                override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                    myLog("get access_token failed -> $t")
                }
            })
    }

}