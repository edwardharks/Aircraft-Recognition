package com.edwardharker.aircraftrecognition.feedback

import android.util.Log
import androidx.work.*
import androidx.work.NetworkType.*

class SubmitFeedbackWorker : Worker() {
    override fun doWork(): Result {
        val message = inputData.getString(MESSAGE_KEY, null)
                ?: throw IllegalArgumentException("No message passed to SubmitFeedbackWorker")

        Log.d("--->", "Hello worker. This is your message: $message")
        return Result.SUCCESS
    }

    companion object {
        private const val MESSAGE_KEY = "MESSAGE_KEY"

        fun createWorkRequest(message: String): WorkRequest {
            return OneTimeWorkRequestBuilder<SubmitFeedbackWorker>()
                .setInputData(mapOf(MESSAGE_KEY to message).toWorkData())
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(CONNECTED)
                        .build()
                )
                .build()
        }
    }
}