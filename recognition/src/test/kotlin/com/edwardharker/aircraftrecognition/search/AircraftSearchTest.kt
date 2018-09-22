package com.edwardharker.aircraftrecognition.search

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import org.junit.Assert.assertThat
import org.junit.Test

class AircraftSearchTest {
    @Test
    fun returnsAircraftWithManufacturersThatStartWithQuery() {
        val boeing = Aircraft(manufacturer = "Boeing")
        val aircraft = listOf(boeing, Aircraft(manufacturer = "Airbus"))

        val expected = listOf(boeing)
        val actual = AircraftSearch.search("boe", aircraft)
        assertThat(actual, sameBeanAs(expected))
    }

    @Test
    fun returnsAircraftWithNamesThatContainQuery() {
        val dreamliner = Aircraft(name = "787 Dreamliner")
        val aircraft = listOf(dreamliner, Aircraft(name = "A380"))

        val expected = listOf(dreamliner)
        val actual = AircraftSearch.search("Dream", aircraft)
        assertThat(actual, sameBeanAs(expected))
    }

    @Test
    fun returnsAircraftWithDisplayNameThatStartWithQuery() {
        val dreamliner = Aircraft(manufacturer = "Boeing", name = "787 Dreamliner")
        val aircraft = listOf(dreamliner, Aircraft(manufacturer = "Airbus", name = "A380"))

        val expected = listOf(dreamliner)
        val actual = AircraftSearch.search("Boeing 787", aircraft)
        assertThat(actual, sameBeanAs(expected))
    }

    @Test
    fun returnsEmptyListForEmptyQuery() {
        val aircraft = listOf(Aircraft(name = "787 Dreamliner"), Aircraft(name = "A380"))

        val expected = emptyList<Aircraft>()
        val actual = AircraftSearch.search("", aircraft)
        assertThat(actual, sameBeanAs(expected))
    }
}
