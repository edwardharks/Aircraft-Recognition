package com.edwardharker.aircraftrecognition.repository.retrofit

import android.util.Log
import com.edwardharker.aircraftrecognition.repository.FeedbackRepository
import com.edwardharker.aircraftrecognition.repository.FeedbackRepository.FeedbackResult
import com.edwardharker.aircraftrecognition.repository.FeedbackRepository.FeedbackResult.Error
import com.edwardharker.aircraftrecognition.repository.FeedbackRepository.FeedbackResult.Success

// TODO test
class RetrofitFeedbackRepository(
    private val feedbackService: FeedbackService
) : FeedbackRepository {
    override fun submitFeedback(message: String): FeedbackResult {
        val response = feedbackService.submitFeedback(message)
        Log.i("Feedback", response.message())
        return if (response.isSuccessful) Success else Error
    }
}
