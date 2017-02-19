package com.edwardharker.aircraftrecognition.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView


class AspectRatioImageView : ImageView {

    var aspectRatio: Float = 2f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = width / aspectRatio
        setMeasuredDimension(width, height.toInt())
    }
}