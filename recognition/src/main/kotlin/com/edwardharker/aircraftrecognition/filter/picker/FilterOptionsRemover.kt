package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.model.Filter
import com.edwardharker.aircraftrecognition.model.SelectedFilterOptionsMap
import com.edwardharker.aircraftrecognition.repository.AircraftRepository

class FilterOptionsRemover(private val aircraftRepository: AircraftRepository) {

    fun removeRedundantFilterOptions(filter: Filter, selectedFilterOptionsMap: SelectedFilterOptionsMap): Filter =
            Filter(filter.name, filter.filterText, filter.filterOptions.filter {
                aircraftRepository.filteredAircraftCount(selectedFilterOptionsMap.plus(Pair(filter.name, it.value))) > 0
            })
}
