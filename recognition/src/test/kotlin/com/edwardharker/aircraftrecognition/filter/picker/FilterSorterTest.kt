package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.model.Filter
import com.edwardharker.aircraftrecognition.model.FilterOption
import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import org.junit.Assert.assertThat
import org.junit.Test

class FilterSorterTest {

    @Test
    fun sortsFiltersByFilterOptions() {
        val oneFilterOptions = Filter(filterOptions = listOf(FilterOption("", "")))
        val twoFilterOptions = Filter(filterOptions = listOf(FilterOption("", ""), FilterOption("", "")))
        val threeFilterOptions = Filter(filterOptions = listOf(FilterOption("", ""), FilterOption("", ""), FilterOption("", "")))
        val filters = listOf(oneFilterOptions, threeFilterOptions, twoFilterOptions)
        val expected = listOf(threeFilterOptions, twoFilterOptions, oneFilterOptions)
        assertThat(expected, sameBeanAs(sortFiltersByFilterOptions(filters)))
    }
}