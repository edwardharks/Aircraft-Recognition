package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.model.Filter

fun sortFiltersByFilterOptions(filters: List<Filter>): List<Filter> =
        filters.sortedByDescending { it.filterOptions.size }
