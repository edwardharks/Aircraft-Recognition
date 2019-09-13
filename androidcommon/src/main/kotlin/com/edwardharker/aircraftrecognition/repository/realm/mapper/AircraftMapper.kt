package com.edwardharker.aircraftrecognition.repository.realm.mapper

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmAircraft
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmPair

fun aircraftToRealmAircraft(aircraft: Aircraft): RealmAircraft {
    return RealmAircraft(
        aircraft.id,
        aircraft.name,
        aircraft.shortDescription,
        aircraft.longDescription,
        aircraft.attribution,
        aircraft.attributionUrl,
        aircraft.metaData.mapToRealmList(::metaDataPairToRealmPair),
        aircraft.filterOptions.mapToRealmList(::mapPairToRealmAircraftFilterOptions),
        aircraft.images.mapToRealmList(::imageToRealmImage),
        aircraft.youtubeVideos.mapToRealmList(::youtubeVideoToRealmYoutubeVideo)
    )
}

fun realmAircraftToAircraft(realmAircraft: RealmAircraft): Aircraft {
    return Aircraft(
        realmAircraft.id,
        realmAircraft.name,
        realmAircraft.shortDescription,
        realmAircraft.longDescription,
        realmAircraft.attribution,
        realmAircraft.attributionUrl,
        realmAircraft.metaData.associate(::realmPairToMetaDataPair),
        realmAircraft.aircraftFilterOptions.associate(::realmAircraftFilterOptionsToPair),
        realmAircraft.images.map(::realmImageToImage),
        realmAircraft.youtubeVideos.map(::realmYoutubeVideoToYoutubeVideo)
    )
}

fun metaDataPairToRealmPair(metaData: Pair<String, String>): RealmPair {
    return RealmPair(metaData.first, metaData.second)
}

fun realmPairToMetaDataPair(realmPair: RealmPair): Pair<String, String> {
    return Pair(realmPair.left, realmPair.right)
}
