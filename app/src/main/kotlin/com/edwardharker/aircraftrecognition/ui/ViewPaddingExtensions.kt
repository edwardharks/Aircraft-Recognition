package com.edwardharker.aircraftrecognition.ui

import android.view.View

// TODO: think of better name
var View.paddingTop2: Int
    get() = paddingTop
    set(value) {
        setPadding(paddingLeft, value, paddingRight, paddingBottom)
    }