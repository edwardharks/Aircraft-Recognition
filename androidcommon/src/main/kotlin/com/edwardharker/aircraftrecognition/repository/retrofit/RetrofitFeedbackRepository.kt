package com.edwardharker.aircraftrecognition.repository.retrofit

import com.edwardharker.aircraftrecognition.model.FeedbackResult
import com.edwardharker.aircraftrecognition.model.FeedbackResult.Error
import com.edwardharker.aircraftrecognition.model.FeedbackResult.Success
import com.edwardharker.aircraftrecognition.repository.FeedbackRepository

// TODO test
class RetrofitFeedbackRepository(
    private val feedbackService: FeedbackService
) : FeedbackRepository {
    override fun submitFeedback(message: String): FeedbackResult {
        val response = feedbackService.submitFeedback(message).execute()
        return if (response.isSuccessful) Success else Error
    }
}
