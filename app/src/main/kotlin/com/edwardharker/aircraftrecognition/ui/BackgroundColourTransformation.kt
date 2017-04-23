package com.edwardharker.aircraftrecognition.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.support.annotation.ColorInt
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.bitmap.BitmapResource.obtain


class BackgroundColourTransformation(context: Context, private val colour: Int) : Transformation<Bitmap> {

    private val bitmapPool = Glide.get(context).bitmapPool

    override fun getId() = "BackgroundColourTransformation-$colour"

    override fun transform(resource: Resource<Bitmap>, outWidth: Int, outHeight: Int): Resource<Bitmap> =
    if (resource.get().hasAlpha()) {
        obtain(resource.get().swapColour(Color.TRANSPARENT, colour), bitmapPool)
    } else {
        obtain(resource.get().copy(resource.get().config, false), bitmapPool)
    }

}

fun Bitmap.swapColour(@ColorInt oldColour: Int, @ColorInt newColour: Int): Bitmap {
    val width = this.width
    val height = this.height
    val newBitmap = this.copy(this.config, true)
    newBitmap.setHasAlpha(true)

    val pixels = IntArray(width * height)
    this.getPixels(pixels, 0, width, 0, 0, width, height)

    (0..width * height - 1)
            .asSequence()
            .filter { pixels[it] == oldColour }
            .forEach { pixels[it] = newColour }

    newBitmap.setPixels(pixels, 0, width, 0, 0, width, height)

    return newBitmap
}