package com.edwardharker.aircraftrecognition.repository.realm.mapper

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmAircraft
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmPair
import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import io.realm.RealmList
import org.junit.Assert.assertThat
import org.junit.Test

class AircraftMapperTest {

    val id = "id"
    val name = "name"
    val shortDescription = "shortDescription"
    val longDescription = "longDescription"
    val manufacturer = "manufacturer"
    val metaDataName = "metaDataName"
    val metaDataValue = "metaDataValue"
    val attribution = "attribution"
    val attributionUrl = "attributionUrl"

    val aircraft = Aircraft(
            id,
            name,
            manufacturer,
            shortDescription,
            longDescription,
            attribution,
            attributionUrl,
            mapOf(Pair(metaDataName, metaDataValue)),
            emptyMap(),
            emptyList(),
            emptyList())

    val realmAircraft = RealmAircraft(
            id,
            name,
            manufacturer,
            shortDescription,
            longDescription,
            attribution,
            attributionUrl,
            RealmList(RealmPair(metaDataName, metaDataValue)),
            RealmList(),
            RealmList(),
            RealmList())

    @Test
    fun mapsAircraftToRealmAircraft() {
        val expected = realmAircraft
        val actual = aircraftToRealmAircraft(aircraft)
        assertThat(actual, sameBeanAs(expected))
    }

    @Test
    fun mapsRealmAircraftToAircraft() {
        val expected = aircraft
        val actual = realmAircraftToAircraft(realmAircraft)
        assertThat(actual, sameBeanAs(expected))
    }
}