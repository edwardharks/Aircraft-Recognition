package com.edwardharker.aircraftrecognition.repository.realm.mapper

import com.edwardharker.aircraftrecognition.model.Image
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmImage
import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import org.junit.Assert.assertThat
import org.junit.Test

class ImageMapperTest {
    @Test
    fun mapsImageToRealmImage() {
        val expected = RealmImage(url, width, height)
        val actual = imageToRealmImage(Image(url, width, height))
        assertThat(actual, sameBeanAs(expected))
    }

    @Test
    fun mapsRealmImageToImage() {
        val expected = Image(url, width, height)
        val actual = realmImageToImage(RealmImage(url, width, height))
        assertThat(actual, sameBeanAs(expected))
    }

    companion object {
        private const val height = 50
        private const val width = 100
        private const val url = "url"
    }
}