package com.edwardharker.aircraftrecognition.repository.realm.mapper

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmAircraft
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmPair

fun aircraftToRealmAircraft(aircraft: Aircraft): RealmAircraft =
        RealmAircraft(
                aircraft.id,
                aircraft.name,
                aircraft.manufacturer,
                aircraft.shortDescription,
                aircraft.longDescription,
                aircraft.attribution,
                aircraft.attributionUrl,
                aircraft.metaData.mapToRealmList(::metaDataPairToRealmPair),
                aircraft.filterOptions.mapToRealmList(::mapPairToRealmAircraftFilterOptions),
                aircraft.images.mapToRealmList(::imageToRealmImage)
        )

fun realmAircraftToAircraft(realmAircraft: RealmAircraft): Aircraft =
        Aircraft(realmAircraft.id,
                realmAircraft.name,
                realmAircraft.manufacturer,
                realmAircraft.shortDescription,
                realmAircraft.longDescription,
                realmAircraft.attribution,
                realmAircraft.attributionUrl,
                realmAircraft.metaData.associate(::realmPairToMetaDataPair),
                realmAircraft.aircraftFilterOptions.associate(::realmAircraftFilterOptionsToPair),
                realmAircraft.images.map(::realmImageToImage)
        )

fun metaDataPairToRealmPair(metaData: Pair<String, String>): RealmPair =
        RealmPair(metaData.first, metaData.second)

fun realmPairToMetaDataPair(realmPair: RealmPair): Pair<String, String> =
        Pair(realmPair.left, realmPair.right)