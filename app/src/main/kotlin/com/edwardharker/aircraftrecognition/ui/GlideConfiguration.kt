package com.edwardharker.aircraftrecognition.ui

import android.content.Context
import android.support.annotation.Keep
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888
import com.bumptech.glide.module.GlideModule

@Keep
class GlideConfiguration : GlideModule {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDecodeFormat(PREFER_ARGB_8888)
    }

    override fun registerComponents(context: Context, glide: Glide) = Unit
}
