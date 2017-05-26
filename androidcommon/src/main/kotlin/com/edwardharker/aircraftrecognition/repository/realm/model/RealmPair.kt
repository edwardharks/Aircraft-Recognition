package com.edwardharker.aircraftrecognition.repository.realm.model

import android.support.annotation.Keep
import io.realm.RealmObject

@Keep
open class RealmPair(
        open var left: String = "",
        open var right: String = ""
) : RealmObject()
