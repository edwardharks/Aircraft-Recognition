package com.edwardharker.aircraftrecognition.ui

import android.support.v4.app.FragmentActivity

class Navigator(val activity: FragmentActivity)

// TODO make val
fun FragmentActivity.navigator() = Navigator(this)

