package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.filter.SelectedFilterOptions
import rx.Observable

class SelectedFilterOptionsBasedFilterPickerShowResetUseCase(
    private val selectedFilterOptions: SelectedFilterOptions
) : FilterPickerShowResetUseCase {
    override fun shouldShowReset(): Observable<Boolean> {
        return selectedFilterOptions
            .asObservable()
            .map { filterOptions ->
                filterOptions.isNotEmpty()
            }
    }
}
