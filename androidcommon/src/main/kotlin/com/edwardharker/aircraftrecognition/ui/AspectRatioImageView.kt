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
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = width / aspectRatio
            setMeasuredDimension(width, height.toInt())
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}
