package com.edwardharker.aircraftrecognition.ui

import android.content.res.Resources


fun dpToPixels(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()
fun pixelsToDp(pixels: Int): Int = (pixels / Resources.getSystem().displayMetrics.density).toInt()