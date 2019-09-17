package com.edwardharker.aircraftrecognition.ui

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.TextView

fun TextView.setTextAppearanceCompat(textAppearance: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        @Suppress("DEPRECATION")
        setTextAppearance(context, textAppearance)
    } else {
        setTextAppearance(textAppearance)
    }
}

var TextView.drawableBottom: Drawable?
    get() = compoundDrawables[3]
    set(value) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            compoundDrawables[0],
            compoundDrawables[1],
            compoundDrawables[2],
            value
        )
    }
