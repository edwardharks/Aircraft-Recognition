package com.edwardharker.aircraftrecognition.analytics.google

import com.edwardharker.aircraftrecognition.androidcommon.BuildConfig
import com.edwardharker.aircraftrecognition.applicationContext
import com.google.android.gms.analytics.GoogleAnalytics

private val tracker = GoogleAnalytics.getInstance(applicationContext()).newTracker(BuildConfig.GA_TRACKING_ID)

fun googleEventAnalytics(): GoogleEventAnalytics = GoogleEventAnalytics(tracker)