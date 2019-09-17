package com.edwardharker.aircraftrecognition.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.TextView

fun TextView.showKeyboard() {
    doOnLayout {
        requestFocus()
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(SHOW_IMPLICIT, 0)
    }
}
