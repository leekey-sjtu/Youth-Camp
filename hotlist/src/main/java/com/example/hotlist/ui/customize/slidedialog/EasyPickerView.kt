package com.example.hotlist.ui.customize.slidedialog

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import androidx.annotation.RequiresApi
import com.example.hotlist.R
import kotlin.math.abs

class EasyPickerView: View {
    private lateinit var mContext: Context
    private lateinit var attrs: AttributeSet
    private var defStyleAttr: Int = 0

    private var attributes: TypedArray
    private var textSize: Int = 0
    private var textColor: Int = 0
    private var textPadding: Int = 0
    private var textMaxScale: Float = 0.toFloat()
    private var textMinAlpha: Float = 0.toFloat()
    private var isRecycleMode: Boolean = false
    private var maxShowNum: Int = 0
    private var curtainRoundedCornerSize: Float = 0f
    private var textPaint: TextPaint
    private var fontMetrics: Paint.FontMetrics
    private var scroller: Scroller
    private var minimumVelocity: Int = 0
    private var maximumVelocity: Int = 0
    private var scaledTouchSlop: Int = 0
    var velocityTracker: VelocityTracker? = null

    constructor(mContext: Context?) : this(mContext,null) {
        if (mContext != null) {
            this.mContext = mContext
        }
    }
    constructor(mContext: Context?, attrs: AttributeSet?): this(mContext,attrs,0){
        if (mContext != null) {
            this.mContext = mContext
        }
        if (attrs != null) {
            this.attrs = attrs
        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(mContext: Context?,attrs: AttributeSet?,defStyleAttr: Int): super(mContext,attrs,defStyleAttr) {
        if (mContext != null) {
            this.mContext = mContext
        }
        if (attrs != null) {
            this.attrs = attrs
        }
        this.defStyleAttr = defStyleAttr

        attributes =
            mContext?.theme!!.obtainStyledAttributes(attrs, R.styleable.EasyPickerView, defStyleAttr, 0)
        textSize =
            attributes.getDimensionPixelSize(R.styleable.EasyPickerView_epvTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, mContext.resources.displayMetrics).toInt())
        textColor = attributes.getColor(R.styleable.EasyPickerView_epvTextColor,Color.BLACK)
        textPadding =
            attributes.getDimensionPixelSize(R.styleable.EasyPickerView_epvTextPadding, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, mContext.resources.displayMetrics).toInt())
        textMaxScale = attributes.getFloat(R.styleable.EasyPickerView_epvTextMaxScale,1.2f)
        textMinAlpha = attributes.getFloat(R.styleable.EasyPickerView_epvTextMinAlpha,0.5f)
        isRecycleMode =
            attributes.getBoolean(R.styleable.EasyPickerView_epvRecycleMode,true)
        maxShowNum = attributes.getInt(R.styleable.EasyPickerView_epvMaxShowNum,3)
        curtainRoundedCornerSize = attributes.getDimensionPixelSize(R.styleable.EasyPickerView_epvCurtainRoundedCorner,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, mContext.resources.displayMetrics).toInt()).toFloat()

        attributes.recycle()

        textPaint = TextPaint()
        textPaint.color = textColor
        textPaint.textSize = textSize.toFloat()
        textPaint.isAntiAlias = true
        fontMetrics = textPaint.fontMetrics
        textHeight = ((fontMetrics.bottom - fontMetrics.top).toInt())

        scroller = Scroller(mContext)
        minimumVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity
        maximumVelocity = ViewConfiguration.get(context).scaledMaximumFlingVelocity
        scaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    // 数据
    private var mDataList: MutableList<String>? = mutableListOf()
    // 中间x坐标
    var cx: Int = 0
    // 中间y坐标
    var cy: Int = 0
    // 文字最大宽度
    var maxTextWidth: Int = 0
    // 文字高度
    private var textHeight: Int = 0
    // 实际内容宽度
    private var contentWidth: Int = 0
    // 实际内容高度
    var contentHeight: Int = 0

    // 按下时的y坐标
    var downY: Int = 0
    // 本次滑动的y坐标偏移值
    var offsetY: Int = 0
    // 在fling之前的offsetY
    var oldOffsetY: Int = 0
    // 当前选中项
    var mCurIndex: Int = 0
    var offsetIndex: Int = 0

    // 回弹距离
    var bounceDistance: Int = 0
    // 是否正处于滑动状态
    var isSliding: Boolean = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var mode = MeasureSpec.getMode(widthMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        contentWidth = (maxTextWidth * textMaxScale + paddingLeft + paddingRight).toInt()
        if (mode != MeasureSpec.EXACTLY) {
            width = contentWidth
        }

        mode = MeasureSpec.getMode(heightMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        contentHeight = contentHeight * maxShowNum + textPadding + maxShowNum
        if (mode != MeasureSpec.EXACTLY) {
            height = contentHeight + paddingTop + paddingBottom
        }

        cx = width / 2
        cy = height / 2

        setMeasuredDimension(width, height)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        addVelocityTracker(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!scroller.isFinished) {
                    scroller.forceFinished(true)
                    finishScroll()
                }
                downY = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                offsetY = (event.y - downY).toInt()
                if (isSliding || abs(offsetY) > scaledTouchSlop) {
                    isSliding = true
                    reDraw()
                }
            }
            MotionEvent.ACTION_UP -> {
                val scrollYVelocity = 2 * getScrollYVelocity() / 3
                if (abs(scrollYVelocity) > minimumVelocity) {
                    oldOffsetY = offsetY
                    scroller.fling(0, 0, 0, scrollYVelocity, 0, 0, -Int.MAX_VALUE, Int.MAX_VALUE)
                    invalidate()
                } else {
                    finishScroll()
                }

                // 没有滑动，则判断点击事件
                if (!isSliding) {
                    if (downY < contentHeight / 3) moveBy(-1) else if (downY > 2 * contentHeight / 3) moveBy(1)
                }
                isSliding = false
                recycleVelocityTracker()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        if (mDataList?.size!! > 0) {
//            canvas.clipRect(
//                cx - contentWidth / 2,
//                cy - contentHeight / 2,
//                cx + contentWidth / 2,
//                cy + contentHeight / 2
//            )
            // 绘制幕布
            val paint = Paint()
            paint.color = Color.GRAY
            paint.style = Paint.Style.FILL
            paint.textSize = textSize * (textMaxScale + 1f)
            paint.alpha = (0.6f * 255).toInt()
            canvas.drawRoundRect(
                0F + paddingLeft,
                cy + (paint.fontMetrics.ascent + paint.fontMetrics.descent) / 2f,
                width.toFloat() - paddingRight,
                cy - (paint.fontMetrics.ascent + paint.fontMetrics.descent) / 1.8f,
                curtainRoundedCornerSize,
                curtainRoundedCornerSize,
                paint
            )

            // 绘制文字，从当前中间项往前、后一共绘制maxShowNum个字
            val size = mDataList!!.size
            val centerPadding = textHeight + textPadding
            val half = maxShowNum / 2 + 1
            for (i in -half..half) {
                var index = mCurIndex - offsetIndex + i
                if (isRecycleMode) {
                    if (index < 0)
                        index = (index + 1) % mDataList!!.size + mDataList!!.size - 1
                    else if (index > mDataList!!.size - 1)
                        index %= mDataList!!.size
                }
                if (index in 0 until size) {
                    // 计算每个字的中间y坐标
                    var tempY = cy + i * centerPadding
                    tempY += offsetY % centerPadding

                    // 根据每个字中间y坐标到cy的距离，计算出scale值
                    val scale = 1.0f - 1.0f * Math.abs(tempY - cy) / centerPadding

                    // 根据textMaxScale，计算出tempScale值，即实际text应该放大的倍数，范围 1~textMaxScale
                    var tempScale = scale * (textMaxScale - 1.0f) + 1.0f
                    tempScale = if (tempScale < 1.0f) 1.0f else tempScale

                    // 计算文字alpha值
                    var textAlpha = textMinAlpha
                    if (textMaxScale != 1f) {
                        val tempAlpha = (tempScale - 1) / (textMaxScale - 1)
                        textAlpha = (1 - textMinAlpha) * tempAlpha + textMinAlpha
                    }
                    textPaint.textSize = textSize * tempScale
                    textPaint.alpha = (255 * textAlpha).toInt()

                    // 绘制
                    val tempFm = textPaint.fontMetrics
                    val text: String = mDataList!![index]
                    val textWidth = textPaint.measureText(text)

                    canvas.drawText(
                        text,
                        cx - textWidth / 2,
                        tempY - (tempFm.ascent + tempFm.descent) / 2,
                        textPaint
                    )


                }
            }
        }
    }


    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            offsetY = oldOffsetY + scroller.currY
            if (!scroller.isFinished) reDraw() else finishScroll()
        }
    }

    private fun addVelocityTracker(event: MotionEvent?){
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker?.addMovement(event)
    }

    private fun recycleVelocityTracker(){
        if (velocityTracker != null) {
            velocityTracker!!.recycle()
            velocityTracker = null
        }
    }

    private fun getScrollYVelocity(): Int {
        velocityTracker!!.computeCurrentVelocity(1000, maximumVelocity.toFloat())
        return velocityTracker!!.yVelocity.toInt()
    }

    private fun reDraw() {
        // mCurIndex需要偏移的量
        val i = offsetY / (textHeight + textPadding)
        if (isRecycleMode || (mCurIndex - i >= 0 && mCurIndex - i < mDataList?.size!!)) {
            if (offsetIndex != i) {
                offsetIndex = i
                if (null != onScrollChangedListener)
                    onScrollChangedListener!!.onScrollChanged(getNowIndex(-offsetIndex))
            }
            postInvalidate()
        } else {
            finishScroll()
        }
    }

    private fun finishScroll() {
        // 判断结束滑动后应该停留在哪个位置
        val centerPadding = textHeight + textPadding
        val v = (offsetY % centerPadding).toFloat()
        if (v > 0.5f * centerPadding) ++offsetIndex else if (v < -0.5f * centerPadding) --offsetIndex

        // 重置mCurIndex
        mCurIndex = getNowIndex(-offsetIndex)

        // 计算回弹的距离
        bounceDistance = offsetIndex * centerPadding - offsetY
        offsetY += bounceDistance

        // 更新
        onScrollChangedListener?.onScrollFinished(mCurIndex)

        // 重绘
        reset()
        postInvalidate()
    }

    private fun getNowIndex(offsetIndex: Int): Int {
        var index = mCurIndex + offsetIndex
        if (isRecycleMode) {
            if (index < 0)
                index = (index + 1) % mDataList!!.size + mDataList!!.size - 1 else if (index > mDataList!!.size - 1) index %= mDataList!!.size
        } else {
            if (index < 0) index = 0 else if (index > mDataList!!.size - 1) index = mDataList!!.size - 1
        }
        return index
    }

    private fun reset() {
        offsetY = 0
        oldOffsetY = 0
        offsetIndex = 0
        bounceDistance = 0
    }

    /**
     * 设置要显示的数据
     *
     * @param mDataList 要显示的数据
     */
    fun setmDataList(dataList: List<String>) {
        this.mDataList!!.clear()
        this.mDataList!!.addAll(dataList)

        // 更新maxTextWidth
        if (dataList.isNotEmpty()) {
            val size = dataList.size
            for (i in 0 until size) {
                val tempWidth = textPaint.measureText(dataList[i])
                if (tempWidth > maxTextWidth) maxTextWidth = tempWidth.toInt()
            }
            mCurIndex = 0
        }
        requestLayout()
        invalidate()
    }

    /**
     * 获取当前状态下，选中的下标
     *
     * @return 选中的下标
     */
    fun getmCurIndex(): Int {
        return getNowIndex(-offsetIndex)
    }

    /**
     * 滚动到指定位置
     *
     * @param index 需要滚动到的指定位置
     */
    private fun moveTo(index: Int) {
        if (index < 0 || index >= mDataList!!.size || mCurIndex == index) return
        if (!scroller.isFinished) scroller.forceFinished(true)
        finishScroll()
        var dy = 0
        val centerPadding = textHeight + textPadding
        dy = if (!isRecycleMode) {
            (mCurIndex - index) * centerPadding
        } else {
            val offsetIndex = mCurIndex - index
            val d1 = abs(offsetIndex) * centerPadding
            val d2 = (mDataList!!.size - abs(offsetIndex)) * centerPadding
            if (offsetIndex > 0) {
                if (d1 < d2) d1 // ascent
                else -d2 // descent
            } else {
                if (d1 < d2) -d1 // descent
                else d2 // ascent
            }
        }
        scroller.startScroll(0, 0, 0, dy, 500)
        invalidate()
    }

    /**
     * 滚动指定的偏移量
     *
     * @param offsetIndex 指定的偏移量
     */
    fun moveBy(offsetIndex: Int) {
        moveTo(getNowIndex(offsetIndex))
    }

    /**
     * 滚动发生变化时的回调接口
     */
    interface OnScrollChangedListener {
        fun onScrollChanged(mCurIndex: Int)
        fun onScrollFinished(mCurIndex: Int)
    }

    private var onScrollChangedListener: OnScrollChangedListener? = null

    fun setOnScrollChangedListener(onScrollChangedListener: OnScrollChangedListener?) {
        this.onScrollChangedListener = onScrollChangedListener
    }
}