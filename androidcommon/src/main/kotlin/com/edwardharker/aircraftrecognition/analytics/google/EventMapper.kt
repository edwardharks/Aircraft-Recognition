package com.edwardharker.aircraftrecognition.analytics.google

import com.edwardharker.aircraftrecognition.analytics.Event
import com.google.android.gms.analytics.HitBuilders

fun Event.toGaEvent(): Map<String, String> =
        HitBuilders.EventBuilder()
                .setCategory("event")
                .setAction(this.eventType.toString())
                .setLabel(this.content)
                .set("itemId", this.itemId)
                .set("itemName", this.itemName)
                .set("origin", this.origin)
                .build()
