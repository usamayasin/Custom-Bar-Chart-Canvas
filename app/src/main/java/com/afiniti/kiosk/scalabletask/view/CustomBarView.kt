package com.afiniti.kiosk.scalabletask.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import android.graphics.RectF

class CustomBarView : View {

    private lateinit var rect: RectF
    private lateinit var paint: Paint
    private var BAR_HEIGHT = 1500f
    private var SCREEN_HEIGHT = 1500f

    constructor(ctx: Context) : super(ctx) {
        init(null)
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }


    private fun init(@Nullable set: AttributeSet?) {

        rect = RectF()
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.GREEN
    }

    fun setBarHeight(barHeight: Float) {
        BAR_HEIGHT = barHeight
        invalidate()
    }

    fun setScreenHeight(screenHeight: Float){
        SCREEN_HEIGHT = screenHeight
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {

        rect.left = 0f
        rect.top = BAR_HEIGHT
        rect.right = 200f
        rect.bottom = SCREEN_HEIGHT
        canvas?.drawRoundRect(rect, 5f, 5f, paint)
    }
}
