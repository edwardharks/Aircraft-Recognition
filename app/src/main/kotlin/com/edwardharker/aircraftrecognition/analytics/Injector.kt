package com.edwardharker.aircraftrecognition.analytics

import com.edwardharker.aircraftrecognition.analytics.firebase.firebaseEventAnalytics

fun eventAnalytics(): EventAnalytics = firebaseEventAnalytics()