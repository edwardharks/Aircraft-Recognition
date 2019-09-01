package com.edwardharker.aircraftrecognition.ui

import androidx.fragment.app.FragmentActivity

class Navigator(val activity: FragmentActivity)

val FragmentActivity.navigator: Navigator
    get() = Navigator(this)
