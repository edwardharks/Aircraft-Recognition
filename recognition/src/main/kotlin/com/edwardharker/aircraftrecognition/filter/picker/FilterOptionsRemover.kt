package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.model.Filter
import com.edwardharker.aircraftrecognition.model.FilterOption
import com.edwardharker.aircraftrecognition.model.SelectedFilterOptionsMap
import com.edwardharker.aircraftrecognition.repository.AircraftRepository

class FilterOptionsRemover(private val aircraftRepository: AircraftRepository) {
    fun removeRedundantFilterOptions(
        filter: Filter,
        selectedFilterOptionsMap: SelectedFilterOptionsMap
    ): Filter {
        return Filter(
            name = filter.name,
            filterText = filter.filterText,
            filterOptions = filter.filterOptions
                .removeFilterOptionsWithNoAircraft(filter, selectedFilterOptionsMap)
        )
    }

    private fun List<FilterOption>.removeFilterOptionsWithNoAircraft(
        filter: Filter,
        selectedFilterOptionsMap: SelectedFilterOptionsMap
    ): List<FilterOption> {
        return filter {
            aircraftRepository
                .filteredAircraftCount(
                    selectedFilterOptionsMap.plus(
                        Pair(
                            filter.name,
                            it.value
                        )
                    )
                ) > 0
        }
    }
}
