package com.edwardharker.aircraftrecognition.youtube

import android.content.Context
import com.google.android.youtube.player.YouTubeApiServiceUtil.isYouTubeApiServiceAvailable
import com.google.android.youtube.player.YouTubeInitializationResult.SUCCESS

class YoutubeAvailabilityChecker(private val context: Context) {
    fun isYoutubeAvailable(): Boolean {
        return isYouTubeApiServiceAvailable(context) == SUCCESS
    }
}
