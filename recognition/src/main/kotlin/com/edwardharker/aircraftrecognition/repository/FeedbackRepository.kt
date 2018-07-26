package com.edwardharker.aircraftrecognition.repository

import com.edwardharker.aircraftrecognition.model.FeedbackResult

interface FeedbackRepository {
    fun submitFeedback(message: String): FeedbackResult
}