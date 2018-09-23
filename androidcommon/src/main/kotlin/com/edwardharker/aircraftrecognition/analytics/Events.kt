package com.edwardharker.aircraftrecognition.analytics

import com.edwardharker.aircraftrecognition.analytics.EventType.IMAGE_LOAD_ERROR
import com.edwardharker.aircraftrecognition.analytics.EventType.SELECT_CONTENT

object Events {
    fun aircraftDetailEvent(aircraftId: String): Event {
        return Event(
            eventType = SELECT_CONTENT,
            itemId = aircraftId,
            content = aircraftId
        )
    }

    fun imageErrorEvent(imageUrl: String, reason: String?): Event {
        return Event(
            eventType = IMAGE_LOAD_ERROR,
            itemName = imageUrl,
            content = reason
        )
    }

    fun similarAircraftClickEvent(aircraftId: String): Event {
        return Event(
            eventType = SELECT_CONTENT,
            itemId = aircraftId,
            content = aircraftId,
            origin = "similaraircraft"
        )
    }

    fun youtubeVideoClickEvent(videoId: String, aircraftId: String): Event {
        return Event(
            eventType = SELECT_CONTENT,
            itemId = videoId,
            content = videoId,
            origin = aircraftId
        )
    }
}