package com.edwardharker.aircraftrecognition.feedback

class FeedbackPresenter(
    private val scheduleFeedbackUseCase: ScheduleFeedbackUseCase
) {
    private var view: FeedbackView? = null

    fun startPresenting(view: FeedbackView) {
        this.view = view
    }

    fun stopPresenting() {
        view = null
    }

    fun submitFeedback(message: String) {
        if (message.isNotBlank()) {
            scheduleFeedbackUseCase.scheduleFeedback(message)
            view?.showSuccess()
        } else {
            view?.showValidationError()
        }
    }
}
