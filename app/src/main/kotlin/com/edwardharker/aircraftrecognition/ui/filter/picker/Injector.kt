package com.edwardharker.aircraftrecognition.ui.filter.picker

import com.edwardharker.aircraftrecognition.filter.picker.FilterPickerPresenter
import com.edwardharker.aircraftrecognition.filter.picker.FilterPickerUseCase
import com.edwardharker.aircraftrecognition.filter.picker.RepositoryFilterPickerUseCase
import com.edwardharker.aircraftrecognition.repository.filterRepository
import rx.android.schedulers.AndroidSchedulers.mainThread
import rx.schedulers.Schedulers.io

private fun filterPickerUseCase(): FilterPickerUseCase = RepositoryFilterPickerUseCase(filterRepository())

fun filterPickerPresenter(): FilterPickerPresenter =
        FilterPickerPresenter(
                mainThread(),
                io(),
                { filterPickerUseCase().filters() })
