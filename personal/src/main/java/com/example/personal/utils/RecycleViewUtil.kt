package com.example.personal.utils

import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.LayoutManager.smoothScrollToPositions(
    recyclerView : RecyclerView,
    position : Int){
    val smoothScroller = LinearSmoothScroller(recyclerView.context)
    smoothScroller.targetPosition = position
    startSmoothScroll(smoothScroller)
}