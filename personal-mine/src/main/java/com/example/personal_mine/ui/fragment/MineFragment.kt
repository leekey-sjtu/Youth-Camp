package com.example.personal_mine.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.common.base.constants.TokenConstants
import com.example.personal.ui.friendList.FriendActivity
import com.example.personal_mine.adapter.VpMineAdapter
import com.example.personal_mine.databinding.FragmentMineBinding
import com.example.personal_mine.viewmodel.MineViewModel
import com.example.personal_mine.viewmodel.VideoMineViewModel
import com.google.android.material.tabs.TabLayoutMediator


class MineFragment : Fragment() {
    private lateinit var mBinding: FragmentMineBinding
    private lateinit var mViewModel: MineViewModel
    private val mVideoMineFragment = VideoMineListFragment()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            com.example.personal_mine.R.layout.fragment_mine, container, false
        )
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG, "MineFragment被创建")
        init()
        initListener()
        initPaper()
        initTabLayout()

    }

    private fun initListener() {
        mBinding.apply {
            val intent = Intent(requireActivity(), FriendActivity::class.java)
            tvFans.setOnClickListener {
                intent.putExtra("fans_or_follow",2)
                startActivity(intent)
            }
            tvFansText.setOnClickListener {
                intent.putExtra("fans_or_follow",2)
                startActivity(intent)
            }
            tvCare.setOnClickListener {
                intent.putExtra("fans_or_follow",1)
                startActivity(intent)
            }
            tvCareText.setOnClickListener {
                intent.putExtra("fans_or_follow",1)
                startActivity(intent)
            }
/*            tvPraise.setOnClickListener {
                Toast.makeText(context,"暂时没开放该接口，敬请等待！",Toast.LENGTH_SHORT).show()
            }
            tvPraiseText.setOnClickListener {
                Toast.makeText(context,"暂时没开放该接口，敬请等待！",Toast.LENGTH_SHORT).show()
            }*/
        }

    }

    companion object {
        private const val TAG = "MineFragment"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MineFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    /**
     * 初始化函数
     */
    private fun init() {
        Log.e(TAG, "init: 打印一下${TokenConstants.ACCESS_TOKEN}", )
        mViewModel = ViewModelProvider(this)[MineViewModel::class.java]
        mViewModel.getData()
        mViewModel.mMineLiveData.observe(viewLifecycleOwner) {
            mBinding.apply {
                tvNameMine.text = it.nickname
                Glide.with(this@MineFragment)
                    .load(it.avatar).into(ivMine)
                mVideoMineFragment.setOpenId(it.open_id)
            }
        }
/*        mViewModel.mFollowLiveData.observe(viewLifecycleOwner) {
            mBinding.apply {
                tvCare.text = it.toString()
            }
        }*/
        mViewModel.mFansLiveData.observe(viewLifecycleOwner) {
            mBinding.apply {
                tvFans.text = it.toString()
            }
        }


    }


    private fun initPaper() {
        val paperAdapter = VpMineAdapter(
            requireActivity(), mutableListOf(
                mVideoMineFragment,
                BlankFragment(),
                BlankFragment(),
                BlankFragment()
            )
        )
        mBinding.vpMine.adapter = paperAdapter
    }


    /**
     * 初始化tabLayout
     */
    private fun initTabLayout() {
        (mBinding).apply {
            TabLayoutMediator(tabLayoutMine, vpMine) { tab, position ->
                tab.text = arrayOf("作品", "私密", "收藏", "喜欢")[position]
            }.attach()
            llVpMine.post {
                llVpMine.layoutParams.height =
                    scrollView.measuredHeight
                llVpMine.requestLayout()
            }
        }
    }


}