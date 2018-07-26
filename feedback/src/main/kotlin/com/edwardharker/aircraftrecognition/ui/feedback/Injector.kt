package com.edwardharker.aircraftrecognition.ui.feedback

import androidx.work.WorkManager
import com.edwardharker.aircraftrecognition.feedback.FeedbackPresenter
import com.edwardharker.aircraftrecognition.feedback.ScheduleFeedbackUseCase
import com.edwardharker.aircraftrecognition.feedback.WorkManagerScheduleFeedbackUseCase

private fun workManager(): WorkManager {
    return WorkManager.getInstance()!!
}

private fun scheduleFeedbackUseCase(): ScheduleFeedbackUseCase {
    return WorkManagerScheduleFeedbackUseCase(
        workManager = workManager()
    )
}

fun feedbackPresenter(): FeedbackPresenter {
    return FeedbackPresenter(
        scheduleFeedbackUseCase = scheduleFeedbackUseCase()
    )
}