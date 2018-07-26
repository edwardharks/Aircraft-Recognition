package com.edwardharker.aircraftrecognition.feedback

import com.edwardharker.aircraftrecognition.model.FeedbackResult
import com.edwardharker.aircraftrecognition.model.FeedbackResult.*
import com.edwardharker.aircraftrecognition.repository.FakeFeedbackRepository
import com.edwardharker.aircraftrecognition.repository.FeedbackRepository
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Test
import org.mockito.Mockito

class RepositorySubmitFeedbackUseCaseTest {
    @Test
    fun `submits feedback`() {
        val mockRepository = Mockito.mock(FeedbackRepository::class.java)
        val useCase = RepositorySubmitFeedbackUseCase(mockRepository)

        useCase.submitFeedback(MESSAGE)

        verify(mockRepository).submitFeedback(MESSAGE)
    }

    @Test
    fun `returns feedback success result`() {
        val useCase = RepositorySubmitFeedbackUseCase(
            feedbackRepository = FakeFeedbackRepository(
                withResult = Success
            )
        )

        val actual = useCase.submitFeedback(MESSAGE)
        assertThat(actual, equalTo<FeedbackResult>(Success))
    }

    @Test
    fun `returns feedback error result`() {
        val useCase = RepositorySubmitFeedbackUseCase(
            feedbackRepository = FakeFeedbackRepository(
                withResult = Error
            )
        )

        val actual = useCase.submitFeedback(MESSAGE)
        assertThat(actual, equalTo<FeedbackResult>(Error))
    }

    companion object {
        private const val MESSAGE = "A lovely message"
    }
}