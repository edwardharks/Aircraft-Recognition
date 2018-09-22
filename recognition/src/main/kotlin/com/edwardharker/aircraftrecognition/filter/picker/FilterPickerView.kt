package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.model.Filter

interface FilterPickerView {
    fun showFilters(filters: List<Filter>)
}
