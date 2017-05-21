package com.edwardharker.aircraftrecognition.analytics

import com.edwardharker.aircraftrecognition.BuildConfig
import com.edwardharker.aircraftrecognition.analytics.firebase.firebaseEventAnalytics
import com.edwardharker.aircraftrecognition.analytics.google.googleEventAnalytics

fun eventAnalytics(): EventAnalytics = ReleaseOnlyCompositeEventAnalytics(!BuildConfig.DEBUG, firebaseEventAnalytics(), googleEventAnalytics())
