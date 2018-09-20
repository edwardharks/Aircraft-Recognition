package com.edwardharker.aircraftrecognition.youtube

import com.edwardharker.aircraftrecognition.applicationContext

fun youtubeAvailabilityChecker(): YoutubeAvailabilityChecker {
    return YoutubeAvailabilityChecker(applicationContext())
}

fun youtubeStandalonePlayerHelper(): YoutubeStandalonePlayerHelper {
    return YoutubeStandalonePlayerHelper
}
