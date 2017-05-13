package com.edwardharker.aircraftrecognition.analytics

import com.edwardharker.aircraftrecognition.analytics.firebase.firebaseEventAnalytics
import com.edwardharker.aircraftrecognition.analytics.google.googleEventAnalytics

fun eventAnalytics(): EventAnalytics = CompositeEventAnalytics(firebaseEventAnalytics(), googleEventAnalytics())
