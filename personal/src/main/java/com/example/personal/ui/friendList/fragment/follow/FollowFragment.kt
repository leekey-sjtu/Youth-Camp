package com.example.personal.ui.friendList.fragment.follow

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.base.baseui.BaseFragment
import com.example.personal.BR
import com.example.personal.R
import com.example.personal.bean.Friend
import com.example.personal.databinding.FragmentFollowBinding
import com.example.personal.ui.friendList.fragment.FriendListAdapter
import com.scwang.smart.refresh.layout.SmartRefreshLayout


class FollowFragment : BaseFragment<FragmentFollowBinding,FollowViewModel>() {

    private lateinit var followListRel : RecyclerView
    private lateinit var followRefresh : SmartRefreshLayout
    private lateinit var followSearchEdit : EditText
    private lateinit var followEditDel : ImageView
    private val followList = mutableListOf<Friend>()

    override fun getLayoutId() = R.layout.fragment_follow

    override fun getVariableId() = BR.followViewModel

    override fun initData(savedInstanceState: Bundle?) {
        //获得view实例
        followListRel = getViewDataBinding().followList
        followRefresh = getViewDataBinding().followRefresh
        followSearchEdit = getViewDataBinding().followSearchEdit
        followEditDel = getViewDataBinding().followEditDel

        //加载关注列表
        val layoutManager = LinearLayoutManager(activity)
        followListRel.layoutManager = layoutManager
        getViewModel().followListData.observe(this){
            val followData = getViewModel().followData.value!!
            if (followData.cursor == 1) followList.clear()
            followList.addAll(it)
            val adapter = FriendListAdapter(followList)
            followListRel.adapter = adapter
            adapter.notifyItemRangeChanged(followData.cursor*10,followData.cursor*10)
            followRefresh.finishLoadMore()
        }
        getViewModel().getFollowList(0)

        //设置下滑加载更多的逻辑
        followRefresh.setOnLoadMoreListener {
            if (getViewModel().followData.value != null){
                if (getViewModel().followData.value!!.has_more) {
                    getViewModel().getFollowList(getViewModel().followData.value!!.cursor)
                }else it.finishLoadMore()
            }
        }

        //设置搜索回车监听和相关逻辑
        followSearchEdit.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyCode != KeyEvent.ACTION_UP) {
                val text = followSearchEdit.text
                if (getViewModel().followData.value != null){
                    val friendList = getViewModel().followData.value!!.list
                    for(i in friendList.indices){
                        if (friendList[i].nickname.contains(text,true)){
                            followListRel.smoothScrollToPosition(i)
                        }
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(requireActivity().window.decorView.windowToken, 0)
                }
                return@setOnKeyListener true
            } else return@setOnKeyListener false
        }

        //设置搜索框文本变化逻辑
        followSearchEdit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (followSearchEdit.text.isNotEmpty()){
                    followEditDel.visibility = View.VISIBLE
                }else followEditDel.visibility = View.GONE
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        //设置搜索框删除键的点击监听
        followEditDel.setOnClickListener {
            followSearchEdit.text.clear()
        }
    }

}