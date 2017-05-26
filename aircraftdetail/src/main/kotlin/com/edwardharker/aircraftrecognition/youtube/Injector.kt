package com.edwardharker.aircraftrecognition.youtube

import com.edwardharker.aircraftrecognition.applicationContext

fun youtubeAvailabilityChecker(): YoutubeAvailabilityChecker = YoutubeAvailabilityChecker(applicationContext())

fun youtubeStandalonePlayerHelper(): YoutubeStandalonePlayerHelper = YoutubeStandalonePlayerHelper()
