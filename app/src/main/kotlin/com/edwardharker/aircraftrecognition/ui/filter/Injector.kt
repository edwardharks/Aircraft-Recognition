package com.edwardharker.aircraftrecognition.ui.filter

import com.edwardharker.aircraftrecognition.filter.InMemorySelectedFilterOptions
import com.edwardharker.aircraftrecognition.filter.SelectedFilterOptions

private val inMemorySelectedFilterOptions = InMemorySelectedFilterOptions()

fun selectedFilterOptions(): SelectedFilterOptions {
    return inMemorySelectedFilterOptions
}
