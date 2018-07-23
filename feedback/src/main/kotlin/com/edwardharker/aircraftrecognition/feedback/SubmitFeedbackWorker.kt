package com.edwardharker.aircraftrecognition.feedback

import androidx.work.*

class SubmitFeedbackWorker : Worker() {
    override fun doWork(): Result {
        val message = inputData.getString(MESSAGE_KEY, null)
                ?: throw IllegalArgumentException("No message passed to SubmitFeedbackWorker")


        return Result.SUCCESS
    }

    companion object {
        private const val MESSAGE_KEY = "MESSAGE_KEY"

        fun createWorkRequest(message: String): WorkRequest {
            return OneTimeWorkRequestBuilder<SubmitFeedbackWorker>()
                .setInputData(mapOf(MESSAGE_KEY to message).toWorkData())
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
        }
    }
}