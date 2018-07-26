package com.edwardharker.aircraftrecognition.repository.retrofit

import android.util.Log
import com.edwardharker.aircraftrecognition.model.FeedbackResult
import com.edwardharker.aircraftrecognition.model.FeedbackResult.*
import com.edwardharker.aircraftrecognition.repository.FeedbackRepository

// TODO test
class RetrofitFeedbackRepository(
    private val feedbackService: FeedbackService
) : FeedbackRepository {
    override fun submitFeedback(message: String): FeedbackResult {
        val response = feedbackService.submitFeedback(message).execute()
        Log.i("Feedback", response.message())
        return if (response.isSuccessful) Success else Error
    }
}
