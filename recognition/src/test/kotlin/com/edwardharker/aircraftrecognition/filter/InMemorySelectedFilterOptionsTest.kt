package com.edwardharker.aircraftrecognition.filter

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import rx.observers.TestSubscriber

class InMemorySelectedFilterOptionsTest {
    private val testSubscriber = TestSubscriber<Map<String, String>>()
    private val selectedFilterOptions = InMemorySelectedFilterOptions()

    @Test
    fun isSelectedAfterSelecting() {
        selectedFilterOptions.select(name, value)
        assertTrue(selectedFilterOptions.isSelected(name, value))
    }

    @Test
    fun isNotSelectedAfterDeselecting() {
        selectedFilterOptions.select(name, value)
        assertTrue(selectedFilterOptions.isSelected(name, value))
        selectedFilterOptions.deselect(name)
        assertFalse(selectedFilterOptions.isSelected(name, value))
    }

    @Test
    fun selectionsUpdateSelectionsObservable() {
        selectedFilterOptions.select(name, value)
        selectedFilterOptions.asObservable().subscribe(testSubscriber)
        val expected = mapOf(Pair(name, value))
        testSubscriber.assertValues(expected)
    }

    @Test
    fun deselectionsUpdateSelectionsObservable() {
        selectedFilterOptions.select(name, value)
        selectedFilterOptions.deselect(name)
        selectedFilterOptions.asObservable().subscribe(testSubscriber)
        testSubscriber.assertValues(emptyMap())
    }

    @Test
    fun selectionsObservableEmitsEmptyWhenNoSelections() {
        selectedFilterOptions.asObservable().subscribe(testSubscriber)
        testSubscriber.assertValues(emptyMap())
    }

    companion object {
        private const val value = "value"
        private const val name = "name"
    }
}