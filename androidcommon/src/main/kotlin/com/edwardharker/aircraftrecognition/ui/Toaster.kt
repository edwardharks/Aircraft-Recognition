package com.edwardharker.aircraftrecognition.ui

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.toast(@StringRes message: Int) {
    toast(getString(message))
}
