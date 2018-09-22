package com.edwardharker.aircraftrecognition.search

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class SearchReducerTest {
    @Test
    fun returnsProperStateForSearchResultsAction() {
        val aircraft = listOf(Aircraft())
        val action = SearchResultsAction(aircraft)
        val expected = SearchState.success(aircraft)
        val actual = SearchReducer.reduce(SearchState.empty(), action)

        assertThat(actual, sameBeanAs(expected))
    }

    @Test
    fun returnsSameStateForSearchQueryChangedAction() {
        val action = QueryChangedAction("query")
        val expected = SearchState.empty()
        val actual = SearchReducer.reduce(expected, action)

        assertThat(actual, equalTo(expected))
    }
}