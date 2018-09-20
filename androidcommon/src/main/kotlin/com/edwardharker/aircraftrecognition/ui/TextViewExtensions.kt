package com.edwardharker.aircraftrecognition.ui

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
