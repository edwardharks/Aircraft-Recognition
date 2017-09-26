package com.edwardharker.aircraftrecognition.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View.MeasureSpec.getSize
import com.github.chrisbanes.photoview.PhotoView

class AspectRatioImageView : PhotoView {

    enum class Aspect {
        WIDTH, HEIGHT
    }

    var aspectRatio: Float = 2f
        set(value) {
            field = value
            requestLayout()
        }
    var fixedAspectRatio = true
        set(value) {
            field = value
            requestLayout()
        }
    var aspect = Aspect.WIDTH
        set(value) {
            field = value
            requestLayout()
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        setZoomable(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (fixedAspectRatio) {
            when (aspect) {
                Aspect.WIDTH -> {
                    val width = getSize(widthMeasureSpec)
                    val height = width / aspectRatio
                    setMeasuredDimension(width, height.toInt())
                }
                Aspect.HEIGHT -> {
                    val height = getSize(heightMeasureSpec)
                    val width = height * aspectRatio
                    setMeasuredDimension(width.toInt(), height)
                }
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}
