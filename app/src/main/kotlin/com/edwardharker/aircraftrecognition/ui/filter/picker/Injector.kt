package com.edwardharker.aircraftrecognition.ui.filter.picker

import com.edwardharker.aircraftrecognition.filter.picker.FilterOptionsRemover
import com.edwardharker.aircraftrecognition.filter.picker.FilterPickerPresenter
import com.edwardharker.aircraftrecognition.filter.picker.FilterPickerResetPresenter
import com.edwardharker.aircraftrecognition.filter.picker.FilterPickerShowResetUseCase
import com.edwardharker.aircraftrecognition.filter.picker.FilterPickerUseCase
import com.edwardharker.aircraftrecognition.filter.picker.RepositoryFilterPickerUseCase
import com.edwardharker.aircraftrecognition.filter.picker.SelectedFilterOptionsBasedFilterPickerShowResetUseCase
import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.repository.filterRepository
import com.edwardharker.aircraftrecognition.ui.filter.selectedFilterOptions
import rx.android.schedulers.AndroidSchedulers.mainThread

private fun filterOptionsRemover(): FilterOptionsRemover {
    return FilterOptionsRemover(aircraftRepository())
}

private fun filterPickerUseCase(): FilterPickerUseCase {
    return RepositoryFilterPickerUseCase(
        filterRepository(),
        selectedFilterOptions(),
        aircraftRepository(),
        filterOptionsRemover()::removeRedundantFilterOptions
    )
}

private fun filterPickerShowResetUseCase(): FilterPickerShowResetUseCase {
    return SelectedFilterOptionsBasedFilterPickerShowResetUseCase(
        selectedFilterOptions = selectedFilterOptions()
    )
}

fun filterPickerPresenter(): FilterPickerPresenter {
    return FilterPickerPresenter(
        mainThread(),
        filterPickerUseCase()::filters
    )
}

fun filterPickerResetPresenter(): FilterPickerResetPresenter {
    return FilterPickerResetPresenter(
        mainThread(),
        filterPickerShowResetUseCase()::shouldShowReset
    )
}
