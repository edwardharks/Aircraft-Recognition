package com.edwardharker.aircraftrecognition.analytics

import com.edwardharker.aircraftrecognition.analytics.EventType.IMAGE_LOAD_ERROR
import com.edwardharker.aircraftrecognition.analytics.EventType.SELECT_CONTENT
import com.edwardharker.aircraftrecognition.analytics.EventType.SELECT_FILTER_OPTION
import com.edwardharker.aircraftrecognition.analytics.Events.aircraftDetailEvent
import com.edwardharker.aircraftrecognition.analytics.Events.imageErrorEvent
import com.edwardharker.aircraftrecognition.analytics.Events.selectFilterOptionEvent
import com.edwardharker.aircraftrecognition.analytics.Events.similarAircraftClickEvent
import com.edwardharker.aircraftrecognition.analytics.Events.youtubeVideoClickEvent
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class EventsTest {
    @Test
    fun `creates aircraft detail event`() {
        val expected = Event(
            eventType = SELECT_CONTENT,
            itemId = AIRCRAFT_ID,
            content = AIRCRAFT_ID
        )

        assertThat(aircraftDetailEvent(AIRCRAFT_ID), equalTo(expected))
    }

    @Test
    fun `creates image error event`() {
        val expected = Event(
            eventType = IMAGE_LOAD_ERROR,
            itemName = IMAGE_URL,
            content = REASON
        )

        assertThat(imageErrorEvent(IMAGE_URL, REASON), equalTo(expected))
    }

    @Test
    fun `creates similar aircraft click event`() {
        val expected = Event(
            eventType = SELECT_CONTENT,
            itemId = AIRCRAFT_ID,
            content = AIRCRAFT_ID,
            origin = "similaraircraft"
        )

        assertThat(similarAircraftClickEvent(AIRCRAFT_ID), equalTo(expected))
    }

    @Test
    fun `creates youtube video click event`() {
        val expected = Event(
            eventType = SELECT_CONTENT,
            itemId = VIDEO_ID,
            content = VIDEO_ID,
            origin = AIRCRAFT_ID
        )

        assertThat(youtubeVideoClickEvent(VIDEO_ID, AIRCRAFT_ID), equalTo(expected))
    }

    @Test
    fun `creates select filter option event`() {
        val expected = Event(
            eventType = SELECT_FILTER_OPTION,
            content = "$FILTER:$FILTER_OPTION"
        )

        val actual = selectFilterOptionEvent(
            filter = FILTER,
            filterOption = FILTER_OPTION
        )

        assertThat(actual, equalTo(expected))
    }

    private companion object {
        private const val AIRCRAFT_ID = "an_aircraft_id"
        private const val VIDEO_ID = "a_video_id"
        private const val IMAGE_URL = "image://url"
        private const val REASON = "a_reason"
        private const val FILTER = "filter"
        private const val FILTER_OPTION = "filter_option"
    }
}