package com.edwardharker.aircraftrecognition.feedback

import com.edwardharker.aircraftrecognition.model.FeedbackResult
import com.edwardharker.aircraftrecognition.repository.FeedbackRepository

class RepositorySubmitFeedbackUseCase(
    private val feedbackRepository: FeedbackRepository
) : SubmitFeedbackUseCase {
    override fun submitFeedback(message: String): FeedbackResult {
        return feedbackRepository.submitFeedback(message)
    }
}