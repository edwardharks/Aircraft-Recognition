package com.edwardharker.aircraftrecognition.feedback

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType.CONNECTED
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.toWorkData
import androidx.work.workDataOf
import com.edwardharker.aircraftrecognition.model.FeedbackResult

class SubmitFeedbackWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {
    private val submitFeedbackUseCase = submitFeedbackUseCase()

    override fun doWork(): Result {
        val message = inputData.getString(MESSAGE_KEY)
                ?: throw IllegalArgumentException("No message passed to SubmitFeedbackWorker")

        val result = submitFeedbackUseCase.submitFeedback(message)

        return when (result) {
            FeedbackResult.Success -> Result.SUCCESS
            FeedbackResult.Error -> Result.RETRY
        }
    }

    companion object {
        private const val MESSAGE_KEY = "MESSAGE_KEY"

        fun createWorkRequest(message: String): WorkRequest {
            return OneTimeWorkRequestBuilder<SubmitFeedbackWorker>()
                .setInputData(workDataOf(MESSAGE_KEY to message))
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(CONNECTED)
                        .build()
                )
                .build()
        }
    }
}
