package com.edwardharker.aircraftrecognition.repository.retrofit

import retrofit2.Response

interface FeedbackService {
    fun submitFeedback(message: String): Response<Unit>
}