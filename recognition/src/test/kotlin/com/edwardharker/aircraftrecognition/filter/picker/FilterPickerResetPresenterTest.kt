package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.filter.FakeSelectedFilterOptions
import com.edwardharker.aircraftrecognition.filter.SelectedFilterOptions
import org.junit.Test
import org.mockito.Mockito
import rx.Observable
import rx.schedulers.Schedulers

class FilterPickerResetPresenterTest {
    @Test
    fun `shows reset`() {
        val mockedView = Mockito.mock(FilterPickerResetView::class.java)

        val presenter = FilterPickerResetPresenter(
            mainScheduler = Schedulers.immediate(),
            shouldShowResetUseCase = { Observable.just(true) },
            selectedFilterOptions = FakeSelectedFilterOptions()
        )

        presenter.startPresenting(mockedView)
        Mockito.verify(mockedView).showReset()
    }

    @Test
    fun `hides reset`() {
        val mockedView = Mockito.mock(FilterPickerResetView::class.java)

        val presenter = FilterPickerResetPresenter(
            mainScheduler = Schedulers.immediate(),
            shouldShowResetUseCase = { Observable.just(false) },
            selectedFilterOptions = FakeSelectedFilterOptions()
        )

        presenter.startPresenting(mockedView)
        Mockito.verify(mockedView).hideReset()
    }

    @Test
    fun `resets filter options`() {
        val mockedSelectedFilterOptions = Mockito.mock(SelectedFilterOptions::class.java)

        val presenter = FilterPickerResetPresenter(
            mainScheduler = Schedulers.immediate(),
            shouldShowResetUseCase = { Observable.just(true) },
            selectedFilterOptions = mockedSelectedFilterOptions
        )

        presenter.resetFilters()

        Mockito.verify(mockedSelectedFilterOptions).deselectAll()
    }
}
