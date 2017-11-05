package com.edwardharker.aircraftrecognition.ui.filter.picker

import com.edwardharker.aircraftrecognition.filter.picker.FilterOptionsRemover
import com.edwardharker.aircraftrecognition.filter.picker.FilterPickerPresenter
import com.edwardharker.aircraftrecognition.filter.picker.FilterPickerUseCase
import com.edwardharker.aircraftrecognition.filter.picker.RepositoryFilterPickerUseCase
import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.repository.filterRepository
import com.edwardharker.aircraftrecognition.ui.filter.selectedFilterOptions
import rx.android.schedulers.AndroidSchedulers.mainThread

private fun filterOptionsRemover(): FilterOptionsRemover = FilterOptionsRemover(aircraftRepository())

private fun filterPickerUseCase(): FilterPickerUseCase =
        RepositoryFilterPickerUseCase(
                filterRepository(),
                selectedFilterOptions(),
                aircraftRepository(),
                filterOptionsRemover()::removeRedundantFilterOptions
        )

fun filterPickerPresenter(): FilterPickerPresenter =
        FilterPickerPresenter(
                mainThread(),
                filterPickerUseCase()::filters
        )
