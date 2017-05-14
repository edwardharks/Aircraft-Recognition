package com.edwardharker.aircraftrecognition.youtube

import android.content.Context
import com.google.android.youtube.player.YouTubeApiServiceUtil
import com.google.android.youtube.player.YouTubeInitializationResult

class YoutubeAvailabilityChecker(private val context: Context) {

    fun isYoutubeAvailable() = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(context) == YouTubeInitializationResult.SUCCESS
}
