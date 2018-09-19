package com.edwardharker.aircraftrecognition.repository.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackService {
    @POST("aircraft/feedback")
    fun submitFeedback(@Body message: String): Call<Unit>
}
