package com.example.personal.ui.friendList.fragment.fan

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.personal.databinding.FragmentFanBinding
import com.example.personal.ui.friendList.fragment.FriendListAdapter
import com.scwang.smart.refresh.layout.SmartRefreshLayout


class FanFragment : BaseFragment<FragmentFanBinding,FanViewModel>() {

    private lateinit var fansListRel : RecyclerView
    private lateinit var fansRefresh : SmartRefreshLayout
    private lateinit var fansSearchEdit : EditText
    private lateinit var fansEditDel : ImageView
    private val fansList = mutableListOf<Friend>()
    
    override fun getLayoutId() = R.layout.fragment_fan

    override fun getVariableId() = BR.fanViewModel

    override fun initData(savedInstanceState: Bundle?) {
        //获得view实例
        fansListRel = getViewDataBinding().fansList
        fansRefresh = getViewDataBinding().fansRefresh
        fansSearchEdit = getViewDataBinding().fansSearchEdit
        fansEditDel = getViewDataBinding().fansEditDel

        //加载关注列表
        val layoutManager = LinearLayoutManager(activity)
        fansListRel.layoutManager = layoutManager
        getViewModel().fansListData.observe(this){
            val fansData = getViewModel().fansData.value!!
            if (fansData.cursor == 1) fansList.clear()
            fansList.addAll(it)
            val adapter = FriendListAdapter(fansList)
            fansListRel.adapter = adapter
            adapter.notifyItemRangeChanged(fansData.cursor*10,fansData.cursor*10)
            fansRefresh.finishLoadMore()
        }
        getViewModel().getFansList(0)

        //设置下滑加载更多的逻辑
        fansRefresh.setOnLoadMoreListener {
            if (getViewModel().fansData.value != null){
                if (getViewModel().fansData.value!!.has_more) {
                    getViewModel().getFansList(getViewModel().fansData.value!!.cursor)
                }else it.finishLoadMore()
            }
        }

        //设置搜索回车监听和相关逻辑
        fansSearchEdit.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyCode != KeyEvent.ACTION_UP) {
                val text = fansSearchEdit.text
                if (getViewModel().fansData.value != null){
                    val friendList = getViewModel().fansData.value!!.list
                    for(i in friendList.indices){
                        if (friendList[i].nickname.contains(text,true)){
                            fansListRel.smoothScrollToPosition(i)
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
        fansSearchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (fansSearchEdit.text.isNotEmpty()){
                    fansEditDel.visibility = View.VISIBLE
                }else fansEditDel.visibility = View.GONE
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        //设置搜索框删除键的点击监听
        fansEditDel.setOnClickListener {
            fansSearchEdit.text.clear()
        }
    }

}