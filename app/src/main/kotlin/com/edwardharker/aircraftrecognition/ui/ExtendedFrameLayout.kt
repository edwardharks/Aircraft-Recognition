package com.edwardharker.aircraftrecognition.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View.MeasureSpec.*
import android.widget.FrameLayout
import com.edwardharker.aircraftrecognition.R
import java.lang.Integer.MAX_VALUE
import java.lang.Math.min


class ExtendedFrameLayout : FrameLayout {

    var maxWidth: Int = MAX_VALUE

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.ExtendedFrameLayout)
            maxWidth = a.getDimensionPixelSize(R.styleable.ExtendedFrameLayout_maxWidth, MAX_VALUE)
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measureMode = getMode(widthMeasureSpec)
        val boundedWidth = makeMeasureSpec(min(getSize(widthMeasureSpec), maxWidth), measureMode)
        super.onMeasure(boundedWidth, heightMeasureSpec)
    }
}
