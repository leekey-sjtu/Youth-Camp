package com.example.personal_mine.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.personal_mine.R
import com.example.personal_mine.adapter.FootAdapter
import com.example.personal_mine.adapter.RvVideoAdapter
import com.example.personal_mine.databinding.FragmentVideoMineListBinding
import com.example.personal_mine.viewmodel.VideoMineViewModel
import kotlinx.coroutines.launch


class VideoMineListFragment : Fragment() {
    private lateinit var mBinding:FragmentVideoMineListBinding
    private lateinit var mViewModel:VideoMineViewModel
    private lateinit var mAdapter :RvVideoAdapter

    companion object {
        private const val TAG = "VideoMineListFragment"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VideoMineListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_mine_list,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initList()
    }

    private fun init(){
        mViewModel = ViewModelProvider(this)[VideoMineViewModel::class.java]
    }

    private fun initList() {
        val layoutManager = GridLayoutManager(requireContext(),3)
        mAdapter = RvVideoAdapter()

        mBinding.apply {
            rvVideoMine.layoutManager = layoutManager
            rvVideoMine.adapter = mAdapter.withLoadStateFooter(FootAdapter())
        }

    }

    fun setOpenId(openId:String){
        lifecycleScope.launch{
            mViewModel.getVideoData(openId).collect{
                Log.e(TAG, "setOpenId: $it", )
                mAdapter.submitData(it)
            }
        }
    }
}