package com.edwardharker.aircraftrecognition.feedback

import com.edwardharker.aircraftrecognition.model.FeedbackResult

interface SubmitFeedbackUseCase {
    fun submitFeedback(message: String): FeedbackResult
}
