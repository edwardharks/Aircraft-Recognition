package com.edwardharker.aircraftrecognition.model

sealed class FeedbackResult {
    object Success : FeedbackResult()
    object Error : FeedbackResult()
}
