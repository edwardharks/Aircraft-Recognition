package com.edwardharker.aircraftrecognition.ui

import android.view.View
import android.view.ViewTreeObserver

fun View.doOnLayout(block: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            block()
        }
    })
}
