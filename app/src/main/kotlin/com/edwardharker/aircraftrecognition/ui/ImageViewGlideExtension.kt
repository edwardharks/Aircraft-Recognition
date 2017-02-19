package com.edwardharker.aircraftrecognition.ui

import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat.getColor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.edwardharker.aircraftrecognition.R
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.ui.AspectRatioImageView

fun AspectRatioImageView.loadAircraftImage(aircraft: Aircraft, imageLoadedListener: (() -> Unit)? = null) {
    if (aircraft.images.isNotEmpty()) {
        val primaryImage = aircraft.images.first()
        Glide.with(context)
                .load(primaryImage.url)
                .placeholder(ColorDrawable(getColor(context, R.color.colorPrimaryLight)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .listener(Listener(imageLoadedListener))
                .into(this)
        aspectRatio = primaryImage.width.toFloat() / primaryImage.height.toFloat()
    } else {
        Glide.clear(this)
        aspectRatio = 2f
    }
    requestLayout()
}

private class Listener(private val imageLoadedListener: (() -> Unit)?) : RequestListener<String, GlideDrawable> {
    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
        imageLoadedListener?.invoke()
        return false
    }

    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
        imageLoadedListener?.invoke()
        return false
    }

}