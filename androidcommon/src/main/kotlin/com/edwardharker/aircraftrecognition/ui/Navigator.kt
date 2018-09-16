package com.edwardharker.aircraftrecognition.ui

import android.support.v4.app.FragmentActivity

class Navigator(val activity: FragmentActivity)

val FragmentActivity.navigator: Navigator
    get() = Navigator(this)
