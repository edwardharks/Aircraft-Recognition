package com.edwardharker.aircraftrecognition.analytics

data class Event(val eventType: EventType,
                 val itemId: String? = null,
                 val itemName: String? = null,
                 val content: String? = null)

enum class EventType {
    SELECT_CONTENT, IMAGE_LOAD_ERROR
}

typealias CustomParams = Map<String, String>