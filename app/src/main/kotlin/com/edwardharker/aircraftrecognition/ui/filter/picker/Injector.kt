package com.edwardharker.aircraftrecognition.ui.filter.picker

import com.edwardharker.aircraftrecognition.filter.picker.*
import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.repository.filterRepository
import com.edwardharker.aircraftrecognition.ui.filter.selectedFilterOptions
import rx.android.schedulers.AndroidSchedulers.mainThread

private fun filterOptionsRemover(): FilterOptionsRemover = FilterOptionsRemover(aircraftRepository())

private fun filterPickerUseCase(): FilterPickerUseCase =
        RepositoryFilterPickerUseCase(
                filterRepository(),
                selectedFilterOptions(),
                filterOptionsRemover()::removeRedundantFilterOptions
        )

fun filterPickerPresenter(): FilterPickerPresenter =
        FilterPickerPresenter(
                mainThread(),
                filterPickerUseCase()::filters
        )
