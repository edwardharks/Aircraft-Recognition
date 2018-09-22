package com.edwardharker.aircraftrecognition.repository.realm.mapper

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmAircraft
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmPair
import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import io.realm.RealmList
import org.junit.Assert.assertThat
import org.junit.Test

class AircraftMapperTest {
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

    private companion object {
        private const val id = "id"
        private const val name = "name"
        private const val shortDescription = "shortDescription"
        private const val longDescription = "longDescription"
        private const val manufacturer = "manufacturer"
        private const val metaDataName = "metaDataName"
        private const val metaDataValue = "metaDataValue"
        private const val attribution = "attribution"
        private const val attributionUrl = "attributionUrl"

        private val aircraft = Aircraft(
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
            emptyList()
        )

        private val realmAircraft = RealmAircraft(
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
            RealmList()
        )
    }
}