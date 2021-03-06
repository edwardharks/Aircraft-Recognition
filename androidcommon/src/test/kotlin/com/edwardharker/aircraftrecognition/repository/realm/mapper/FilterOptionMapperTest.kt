package com.edwardharker.aircraftrecognition.repository.realm.mapper

import com.edwardharker.aircraftrecognition.repository.realm.model.RealmAircraftFilterOption
import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import org.junit.Assert.assertThat
import org.junit.Test

class FilterOptionMapperTest {
    @Test
    fun convertsPairToRealmFilterOptions() {
        val expected = RealmAircraftFilterOption(id, name, value)
        val actual = mapPairToRealmAircraftFilterOptions(Pair(name, value))
        assertThat(actual, sameBeanAs(expected))
    }

    @Test
    fun convertsRealmFilterOptionsMap() {
        val expected = Pair(name, value)
        val actual = realmAircraftFilterOptionsToPair(RealmAircraftFilterOption(id, name, value))
        assertThat(actual, sameBeanAs(expected))
    }

    private companion object {
        private const val name = "name"
        private const val value = "value"
        private const val id = "$name-$value"
    }
}
