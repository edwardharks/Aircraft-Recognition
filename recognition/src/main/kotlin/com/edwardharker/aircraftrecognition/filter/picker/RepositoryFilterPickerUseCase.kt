package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.filter.SelectedFilterOptions
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.model.Filter
import com.edwardharker.aircraftrecognition.model.SelectedFilterOptionsMap
import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import com.edwardharker.aircraftrecognition.repository.FilterRepository
import rx.Observable
import rx.Observable.combineLatest


class RepositoryFilterPickerUseCase(
    private val filterRepository: FilterRepository,
    private val selectedFilterOptions: SelectedFilterOptions,
    private val aircraftRepository: AircraftRepository,
    private val filterOptionsRemover: (Filter, SelectedFilterOptionsMap) -> Filter
) : FilterPickerUseCase {
    override fun filters(): Observable<List<Filter>> =
        combineLatest(
            filterRepository.filters(),
            selectedFilterOptions.asObservable(),
            aircraftRepository.allAircraft().filter { it.isNotEmpty() },
            ::AircraftFilterHolder
        ).map { (filters, selectedFilterOptionsMap) ->
            filters.map { filterOptionsRemover.invoke(it, selectedFilterOptionsMap) }
        }
}

private data class AircraftFilterHolder(
    val filters: List<Filter>,
    val selectedFilterOptions: SelectedFilterOptionsMap,
    val aircraft: List<Aircraft>
)
