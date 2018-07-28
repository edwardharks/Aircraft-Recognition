package com.edwardharker.aircraftrecognition.ui

import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.widget.Toast

fun Fragment.toast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.toast(@StringRes message: Int) {
    toast(getString(message))
}
