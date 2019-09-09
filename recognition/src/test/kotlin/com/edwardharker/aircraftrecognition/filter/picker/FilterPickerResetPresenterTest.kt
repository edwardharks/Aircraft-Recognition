package com.edwardharker.aircraftrecognition.filter.picker

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
            shouldShowResetUseCase = { Observable.just(true) }
        )

        presenter.startPresenting(mockedView)
        Mockito.verify(mockedView).showReset()
    }

    @Test
    fun `hides reset`() {
        val mockedView = Mockito.mock(FilterPickerResetView::class.java)

        val presenter = FilterPickerResetPresenter(
            mainScheduler = Schedulers.immediate(),
            shouldShowResetUseCase = { Observable.just(false) }
        )

        presenter.startPresenting(mockedView)
        Mockito.verify(mockedView).hideReset()
    }
}
