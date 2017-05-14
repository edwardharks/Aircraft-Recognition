package com.edwardharker.aircraftrecognition.youtube

import android.app.Activity
import com.edwardharker.aircraftrecognition.BuildConfig
import com.google.android.youtube.player.YouTubeStandalonePlayer

class YoutubeStandalonePlayerHelper {

    fun launchYoutubeStandalonePlayer(activity: Activity, videoId: String) {
        val createVideoIntent = YouTubeStandalonePlayer
                .createVideoIntent(activity, BuildConfig.YOUTUBE_API_KEY, videoId)
        activity.startActivity(createVideoIntent)
    }
}
