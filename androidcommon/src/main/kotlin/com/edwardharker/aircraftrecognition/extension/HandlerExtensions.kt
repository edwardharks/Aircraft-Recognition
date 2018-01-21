package com.edwardharker.aircraftrecognition.extension

import android.os.Handler

fun Handler.postDelayed(delay: Long, runnable: () -> Unit) {
    postDelayed(runnable, delay)
}
