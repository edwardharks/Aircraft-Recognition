package com.edwardharker.aircraftrecognition.analytics.firebase

import com.edwardharker.aircraftrecognition.applicationContext
import com.google.firebase.analytics.FirebaseAnalytics

private fun firbaseAnalytics(): FirebaseAnalytics = FirebaseAnalytics.getInstance(applicationContext())

fun firebaseEventAnalytics(): FirebaseEventAnalytics = FirebaseEventAnalytics(firbaseAnalytics())