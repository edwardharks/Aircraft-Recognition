package com.edwardharker.aircraftrecognition.repository.realm.model

import android.support.annotation.Keep
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

@Keep
open class RealmYoutubeVideo(
        @PrimaryKey open var videoId: String = ""
) : RealmObject()