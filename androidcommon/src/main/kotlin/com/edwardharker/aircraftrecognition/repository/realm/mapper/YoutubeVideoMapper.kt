package com.edwardharker.aircraftrecognition.repository.realm.mapper

import com.edwardharker.aircraftrecognition.model.YoutubeVideo
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmYoutubeVideo

fun youtubeVideoToRealmYoutubeVideo(youtubeVideo: YoutubeVideo): RealmYoutubeVideo =
        RealmYoutubeVideo(youtubeVideo.videoId)

fun realmYoutubeVideoToYoutubeVideo(realmYoutubeVideo: RealmYoutubeVideo): YoutubeVideo =
        YoutubeVideo(realmYoutubeVideo.videoId)