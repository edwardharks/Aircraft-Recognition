package com.edwardharker.aircraftrecognition.ui

import android.app.Activity

class ActivityLauncher(val activity: Activity)

fun Activity.activityLauncher(): ActivityLauncher = ActivityLauncher(this)

