package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.model.Filter
import com.edwardharker.aircraftrecognition.repository.FilterRepository
import rx.Observable

class RepositoryFilterPickerUseCase(private val filterRepository: FilterRepository) : FilterPickerUseCase {

    override fun filters(): Observable<List<Filter>> = filterRepository.filters()
}