package com.edwardharker.aircraftrecognition.ui.filter.results

import com.edwardharker.aircraftrecognition.filter.results.FilterResultsPresenter
import com.edwardharker.aircraftrecognition.filter.results.FilterResultsUseCase
import com.edwardharker.aircraftrecognition.filter.results.RepositoryFilterResultsUseCase
import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.ui.filter.selectedFilterOptions
import rx.android.schedulers.AndroidSchedulers.mainThread

fun filterResultsPresenter(): FilterResultsPresenter =
        FilterResultsPresenter(
                mainThread(),
                { filterResultsUseCase().filteredAircraft() })

private fun filterResultsUseCase(): FilterResultsUseCase =
        RepositoryFilterResultsUseCase(selectedFilterOptions(), aircraftRepository())
