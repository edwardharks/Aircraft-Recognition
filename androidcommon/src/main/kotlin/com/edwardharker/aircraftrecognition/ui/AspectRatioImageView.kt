package com.edwardharker.aircraftrecognition.ui

import android.content.Context
import android.util.AttributeSet
import com.github.chrisbanes.photoview.PhotoView

class AspectRatioImageView : PhotoView {

    var aspectRatio: Float = 2f
    var fixedAspectRatio = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        setZoomable(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (fixedAspectRatio) {
            if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
                val width = MeasureSpec.getSize(widthMeasureSpec)
                val height = width / aspectRatio
                setMeasuredDimension(width, height.toInt())
            } else {
                val height = MeasureSpec.getSize(heightMeasureSpec)
                val width = height * aspectRatio
                setMeasuredDimension(width.toInt(), height)
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}
