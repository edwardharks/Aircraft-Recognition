package com.edwardharker.aircraftrecognition.repository

import com.edwardharker.aircraftrecognition.model.FeedbackResult

class FakeFeedbackRepository(
    private val withResult: FeedbackResult
) : FeedbackRepository {
    override fun submitFeedback(message: String): FeedbackResult = withResult
}