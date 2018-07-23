package com.edwardharker.aircraftrecognition.feedback

import androidx.work.WorkManager

class WorkManagerScheduleFeedbackUseCase(
    private val workManager: WorkManager
) : ScheduleFeedbackUseCase {
    override fun scheduleFeedback(message: String) {
        workManager.enqueue(SubmitFeedbackWorker.createWorkRequest(message))
    }
}
