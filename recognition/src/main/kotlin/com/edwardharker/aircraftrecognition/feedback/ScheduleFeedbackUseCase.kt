package com.edwardharker.aircraftrecognition.feedback

interface ScheduleFeedbackUseCase {
    fun scheduleFeedback(message: String)

    companion object {
        val NO_OP = object : ScheduleFeedbackUseCase {
            override fun scheduleFeedback(message: String) {}
        }
    }
}
