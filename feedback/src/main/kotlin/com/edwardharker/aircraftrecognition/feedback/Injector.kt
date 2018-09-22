package com.edwardharker.aircraftrecognition.feedback

import androidx.work.WorkManager
import com.edwardharker.aircraftrecognition.repository.feedbackRepository

private fun workManager(): WorkManager {
    return WorkManager.getInstance()
}

private fun scheduleFeedbackUseCase(): ScheduleFeedbackUseCase {
    return WorkManagerScheduleFeedbackUseCase(
        workManager = workManager()
    )
}

fun submitFeedbackUseCase(): SubmitFeedbackUseCase {
    return RepositorySubmitFeedbackUseCase(
        feedbackRepository = feedbackRepository()
    )
}

fun feedbackPresenter(): FeedbackPresenter {
    return FeedbackPresenter(
        scheduleFeedbackUseCase = scheduleFeedbackUseCase()
    )
}
