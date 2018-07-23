package com.edwardharker.aircraftrecognition.repository

interface FeedbackRepository {
    sealed class FeedbackResult {
        object Success : FeedbackResult()
        object Error : FeedbackResult()
    }

    fun submitFeedback(message: String): FeedbackResult
}