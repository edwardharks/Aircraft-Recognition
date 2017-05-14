package com.edwardharker.aircraftrecognition.repository.realm.mapper

import com.edwardharker.aircraftrecognition.model.YoutubeVideo
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmYoutubeVideo
import com.shazam.shazamcrest.MatcherAssert.assertThat
import com.shazam.shazamcrest.matcher.Matchers
import org.junit.Test

class YoutubeVideoMapperTest {

    private val videoId = "aVideoId"

    @Test
    fun mapsYoutubeVideoToRealmYoutubeVideo() {
        val expected = RealmYoutubeVideo(videoId)
        val actual = youtubeVideoToRealmYoutubeVideo(YoutubeVideo(videoId))
        assertThat(actual, Matchers.sameBeanAs(expected))
    }

    @Test
    fun mapsRealmYoutubeVideoToYoutubeVideo() {
        val expected = YoutubeVideo(videoId)
        val actual = realmYoutubeVideoToYoutubeVideo(RealmYoutubeVideo(videoId))
        assertThat(actual, Matchers.sameBeanAs(expected))
    }
}
