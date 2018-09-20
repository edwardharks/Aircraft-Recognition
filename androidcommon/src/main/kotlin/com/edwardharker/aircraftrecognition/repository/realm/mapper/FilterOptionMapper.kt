package com.edwardharker.aircraftrecognition.repository.realm.mapper

import com.edwardharker.aircraftrecognition.repository.realm.model.RealmAircraftFilterOption

fun mapPairToRealmAircraftFilterOptions(
    filterOptionPair: Pair<String, String>
): RealmAircraftFilterOption {
    return RealmAircraftFilterOption(
        createFilterOptionId(filterOptionPair.first, filterOptionPair.second),
        filterOptionPair.first,
        filterOptionPair.second
    )
}

fun realmAircraftFilterOptionsToPair(
    realmAircraftFilterOption: RealmAircraftFilterOption
): Pair<String, String> {
    return Pair(realmAircraftFilterOption.name, realmAircraftFilterOption.value)
}

fun createFilterOptionId(key: String, value: String): String {
    return "$key-$value"
}
