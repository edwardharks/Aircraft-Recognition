package com.edwardharker.aircraftrecognition.feedback

import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.mockito.Mockito

class FeedbackPresenterTest {
    @Test
    fun `submits feedback`() {
        val mockSubmitFeedbackUseCase = Mockito.mock(ScheduleFeedbackUseCase::class.java)
        val presenter = FeedbackPresenter(mockSubmitFeedbackUseCase)

        presenter.submitFeedback(MESSAGE)

        verify(mockSubmitFeedbackUseCase).scheduleFeedback(MESSAGE)
    }

    @Test
    fun `shows success`() {
        val mockView = Mockito.mock(FeedbackView::class.java)
        val presenter = FeedbackPresenter(ScheduleFeedbackUseCase.NO_OP)

        presenter.startPresenting(mockView)
        presenter.submitFeedback(MESSAGE)

        verify(mockView).showSuccess()
    }

    @Test
    fun `never shows success after stopPresenting`() {
        val mockView = Mockito.mock(FeedbackView::class.java)
        val presenter = FeedbackPresenter(ScheduleFeedbackUseCase.NO_OP)

        presenter.startPresenting(mockView)
        presenter.stopPresenting()
        presenter.submitFeedback(MESSAGE)

        verify(mockView, never()).showSuccess()
    }

    companion object {
        private const val MESSAGE = "a nice message"
    }
}
