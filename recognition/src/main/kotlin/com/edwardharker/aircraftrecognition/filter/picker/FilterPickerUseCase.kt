package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.model.Filter
import rx.Observable

interface FilterPickerUseCase {
    fun filters(): Observable<List<Filter>>
}
