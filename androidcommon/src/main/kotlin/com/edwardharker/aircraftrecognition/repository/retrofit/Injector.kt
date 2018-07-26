package com.edwardharker.aircraftrecognition.repository.retrofit

import com.edwardharker.aircraftrecognition.repository.FeedbackRepository

private fun aircraftService(): AircraftService = RetrofitAdapter.aircraftService

private fun feedbackService(): FeedbackService = RetrofitAdapter.feedbackService

fun retrofitAircraftRepository(): RetrofitAircraftRepository {
    return RetrofitAircraftRepository(aircraftService = aircraftService())
}

fun retrofitFeedbackRepository(): FeedbackRepository {
    return RetrofitFeedbackRepository(feedbackService = feedbackService())
}

