package com.edwardharker.aircraftrecognition.ui

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

@ColorInt
fun Context.getColour(@ColorRes colour: Int): Int {
    return ContextCompat.getColor(this, colour)
}
