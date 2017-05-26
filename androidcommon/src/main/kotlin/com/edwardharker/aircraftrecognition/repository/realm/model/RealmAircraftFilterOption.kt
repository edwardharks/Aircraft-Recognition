package com.edwardharker.aircraftrecognition.repository.realm.model

import android.support.annotation.Keep
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

@Keep
open class RealmAircraftFilterOption(
        @PrimaryKey open var id: String = "",
        open var name: String = "",
        open var value: String = ""
) : RealmObject()
