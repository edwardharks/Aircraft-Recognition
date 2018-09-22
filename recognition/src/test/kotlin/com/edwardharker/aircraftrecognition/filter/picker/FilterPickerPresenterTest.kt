package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.model.Filter
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import rx.Observable
import rx.schedulers.Schedulers.immediate

class FilterPickerPresenterTest {
    @Test
    fun showsFilters() {
        val filters = listOf(Filter())
        val mockedView = mock(FilterPickerView::class.java)

        val presenter = FilterPickerPresenter(
            mainScheduler = immediate(),
            filterPickerUseCase = { Observable.just(filters) }
        )

        presenter.startPresenting(mockedView)
        verify(mockedView).showFilters(filters)
    }
}