package com.edwardharker.aircraftrecognition.ui

import android.content.res.Resources


fun Int.dpToPixels(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun Int.pixelsToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}
