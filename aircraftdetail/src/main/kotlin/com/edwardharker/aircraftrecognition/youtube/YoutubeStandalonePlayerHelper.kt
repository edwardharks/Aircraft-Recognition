package com.edwardharker.aircraftrecognition.youtube

import android.app.Activity
import android.util.Log
import com.edwardharker.aircraftrecognition.aircraftdetail.BuildConfig.YOUTUBE_API_KEY
import com.google.android.youtube.player.YouTubeStandalonePlayer

object YoutubeStandalonePlayerHelper {
    fun launchYoutubeStandalonePlayer(activity: Activity, videoId: String) {
        try {
            val createVideoIntent = YouTubeStandalonePlayer
                    .createVideoIntent(activity, YOUTUBE_API_KEY, videoId)
            activity.startActivity(createVideoIntent)
        } catch (ignored: Exception) {
            Log.e("Youtube", "Failed to start youtube player", ignored)
        }
    }
}
