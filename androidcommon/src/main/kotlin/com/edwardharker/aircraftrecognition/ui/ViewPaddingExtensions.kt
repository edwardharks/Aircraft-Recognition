package com.edwardharker.aircraftrecognition.ui

import android.view.View

// TODO: this should be an extension property
var View.paddingTop2: Int
    get() = paddingTop
    set(value) {
        setPadding(paddingLeft, value, paddingRight, paddingBottom)
    }