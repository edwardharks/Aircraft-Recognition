package com.edwardharker.aircraftrecognition.ui

import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat.getColor
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.edwardharker.aircraftrecognition.R
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.model.Image

fun AspectRatioImageView.loadAircraftImage(aircraft: Aircraft, imageLoadedListener: (() -> Unit)? = null) {
    if (aircraft.images.isNotEmpty()) {
        loadAircraftImage(aircraft.images.first(), imageLoadedListener)
    } else {
        Glide.clear(this)
        aspectRatio = 2f
    }
    requestLayout()
}

fun AspectRatioImageView.loadAircraftImage(image: Image, imageLoadedListener: (() -> Unit)? = null) {
    loadImage(image, imageLoadedListener)
    aspectRatio = image.width.toFloat() / image.height.toFloat()
}

fun ImageView.loadImage(image: Image, imageLoadedListener: (() -> Unit)? = null) {
    Glide.with(context)
            .load(image.url)
            .placeholder(ColorDrawable(getColor(context, R.color.colorPrimaryLight)))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .listener(Listener(image, imageLoadedListener))
            .into(this)
    requestLayout()
}

private class Listener(val image: Image, val imageLoadedListener: (() -> Unit)?) : RequestListener<String, GlideDrawable> {
    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
        imageLoadedListener?.invoke()
        return false
    }

    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
        resource?.let {
            if (it.intrinsicWidth != image.width || it.intrinsicHeight != image.height) {
                Log.w("ImageLoadListener", "Image downloaded from the internet does not match the expected size. Url: ${image.url}")
            }
        }
        imageLoadedListener?.invoke()
        return false
    }
}
