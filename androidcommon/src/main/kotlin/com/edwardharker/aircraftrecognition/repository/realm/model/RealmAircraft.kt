package com.edwardharker.aircraftrecognition.repository.realm.model

import androidx.annotation.Keep
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

@Keep
open class RealmAircraft(
    @PrimaryKey open var id: String = "",
    open var name: String = "",
    open var shortDescription: String = "",
    open var longDescription: String = "",
    open var attribution: String = "",
    open var attributionUrl: String = "",
    open var metaData: RealmList<RealmPair> = RealmList(),
    open var aircraftFilterOptions: RealmList<RealmAircraftFilterOption> = RealmList(),
    open var images: RealmList<RealmImage> = RealmList(),
    open var youtubeVideos: RealmList<RealmYoutubeVideo> = RealmList()
) : RealmObject()
