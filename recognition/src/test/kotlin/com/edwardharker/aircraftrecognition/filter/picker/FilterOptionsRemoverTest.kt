package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.model.Filter
import com.edwardharker.aircraftrecognition.model.FilterOption
import com.edwardharker.aircraftrecognition.repository.FakeAircraftRepository
import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import org.junit.Assert.assertThat
import org.junit.Test

class FilterOptionsRemoverTest {

    private val value = "value"
    private val label = "label"
    private val name = "name"
    private val filterText = "filtertext"
    private val filterOption = FilterOption(value, label)

    @Test
    fun removesFilterOptionsWithNoAircraftResults() {
        val filterOptionsMap = mapOf(Pair(name, value))
        val filterOptionRemover = FilterOptionsRemover(FakeAircraftRepository().thatReturns(0, filterOptionsMap))
        val filter = Filter(name, filterText, listOf(filterOption))
        val expected = Filter(name, filterText, emptyList())
        assertThat(filterOptionRemover.removeRedundantFilterOptions(filter, emptyMap()), sameBeanAs(expected))
    }

    @Test
    fun doesNotRemoveFilterOptionsWithAircraftResults() {
        val filterOptionsMap = mapOf(Pair(name, value))
        val filterOptionRemover = FilterOptionsRemover(FakeAircraftRepository().thatReturns(1, filterOptionsMap))
        val filter = Filter(name, filterText, listOf(filterOption))
        assertThat(filterOptionRemover.removeRedundantFilterOptions(filter, emptyMap()), sameBeanAs(filter))
    }

}