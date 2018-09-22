package com.edwardharker.aircraftrecognition.ui

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat

@ColorInt
fun Context.getColour(@ColorRes colour: Int): Int {
    return ContextCompat.getColor(this, colour)
}
