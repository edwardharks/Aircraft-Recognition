package com.edwardharker.aircraftrecognition.analytics.firebase

import android.os.Bundle
import com.edwardharker.aircraftrecognition.analytics.Event
import com.edwardharker.aircraftrecognition.analytics.EventType
import com.google.firebase.analytics.FirebaseAnalytics

fun Event.toBundle() = Bundle().apply {
    putString(FirebaseAnalytics.Param.ITEM_ID, itemId)
    putString(FirebaseAnalytics.Param.ITEM_NAME, itemName)
    putString(FirebaseAnalytics.Param.CONTENT, content)
    putString(FirebaseAnalytics.Param.ORIGIN, origin)
}

fun EventType.toFirebaseEventType() =
        when (this) {
            EventType.SELECT_CONTENT -> FirebaseAnalytics.Event.SELECT_CONTENT
            EventType.IMAGE_LOAD_ERROR -> "image_load_error"
        }
