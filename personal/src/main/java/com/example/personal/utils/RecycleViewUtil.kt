package com.example.personal.utils

import androidx.recyclerview.widget.RecyclerView

//标记是否需要二次滑动
private var shouldMove = false

//需要滑动到的item位置
private var mPosition = 0

fun smoothScrollToPositions(
    recyclerView: RecyclerView,
    position: Int
){
    // 获取RecyclerView的第一个可见位置
    val firstItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0))
    // 获取RecyclerView的最后一个可见位置
    val lastItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(recyclerView.childCount - 1))
    if (position < firstItem) {
        // 指定item在第一个可见item之前
        recyclerView.smoothScrollToPosition(position)
    } else if (position <= lastItem) {
        // 指定item在可见范围内，即在第一个可见item之后，最后一个可见item之前
        val positions = position - firstItem
        if (positions >= 0 && positions < recyclerView.childCount) {
            // 计算指定item的view到顶部的距离
            val top = recyclerView.getChildAt(positions).top
            // 手动滑动到顶部
            recyclerView.smoothScrollBy(0, top)
        }
    } else {
        // 指定item在最后一个可见item之后，用smoothScrollToPosition滑动到可见范围
        // 再监听RecyclerView的滑动，对其进行二次滑动到顶部
        recyclerView.smoothScrollToPosition(position)
        mPosition = position
        shouldMove = true
    }
//    val smoothScroller = LinearSmoothScroller(recyclerView.context)
//    smoothScroller.targetPosition = position
//    startSmoothScroll(smoothScroller)
}