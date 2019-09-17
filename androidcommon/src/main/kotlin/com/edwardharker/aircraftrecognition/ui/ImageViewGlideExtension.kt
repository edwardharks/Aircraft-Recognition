package com.edwardharker.aircraftrecognition.ui

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.edwardharker.aircraftrecognition.analytics.Events.imageErrorEvent
import com.edwardharker.aircraftrecognition.analytics.eventAnalytics
import com.edwardharker.aircraftrecognition.androidcommon.R
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.model.Image

fun AspectRatioImageView.loadAircraftImage(
    aircraft: Aircraft,
    imageLoadedListener: (() -> Unit)? = null
) {
    if (aircraft.images.isNotEmpty()) {
        loadAircraftImage(aircraft.images.first(), imageLoadedListener)
    } else {
        Glide.clear(this)
        aspectRatio = 2f
    }
}

fun AspectRatioImageView.loadAircraftImage(
    image: Image,
    imageLoadedListener: (() -> Unit)? = null
) {
    loadImage(image, imageLoadedListener)
    aspectRatio = image.width.toFloat() / image.height.toFloat()
}

fun ImageView.loadImage(
    image: Image,
    imageLoadedListener: (() -> Unit)? = null
) {
    Glide.with(context)
        .load(image.url)
        .asBitmap()
        .placeholder(context.getDrawableCompat(R.drawable.fallback_image))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontAnimate()
        .listener(Listener(imageLoadedListener))
        .fitCenter()
        .into(this)
}

private class Listener(val imageLoadedListener: (() -> Unit)?) : RequestListener<String, Bitmap> {
    override fun onException(
        error: Exception?,
        model: String?,
        target: Target<Bitmap>?,
        isFirstResource: Boolean
    ): Boolean {
        imageLoadedListener?.invoke()
        eventAnalytics().logEvent(imageErrorEvent(model ?: "Unknown", error?.message ?: "Unknown"))
        return false
    }

    override fun onResourceReady(
        resource: Bitmap?,
        model: String?,
        target: Target<Bitmap>?,
        isFromMemoryCache: Boolean,
        isFirstResource: Boolean
    ): Boolean {
        imageLoadedListener?.invoke()
        return false
    }
}
