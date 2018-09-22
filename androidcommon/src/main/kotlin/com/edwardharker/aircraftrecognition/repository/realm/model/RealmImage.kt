package com.edwardharker.aircraftrecognition.repository.realm.model

import android.support.annotation.Keep
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

@Keep
open class RealmImage(
    @PrimaryKey open var url: String = "",
    open var width: Int = 0,
    open var height: Int = 0
) : RealmObject()
