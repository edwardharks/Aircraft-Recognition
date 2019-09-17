package com.edwardharker.aircraftrecognition.ui.feedback

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import com.edwardharker.aircraftrecognition.feedback.FeedbackView
import com.edwardharker.aircraftrecognition.feedback.feedbackPresenter
import com.edwardharker.aircraftrecognition.ui.Navigator
import com.edwardharker.aircraftrecognition.ui.showKeyboard
import com.edwardharker.aircraftrecognition.ui.toast

fun Navigator.launchFeedbackDialog() {
    val fragmentManager = activity.supportFragmentManager
    val fragment = FeedbackDialogFragment.newInstance()
    fragment.show(fragmentManager, FeedbackDialogFragment.TAG)
}

class FeedbackDialogFragment : AppCompatDialogFragment(), FeedbackView {
    private val presenter = feedbackPresenter()
    private val handler = Handler()
    private val dismissRunnable = { dismiss() }

    private lateinit var enterFeedbackGroup: View
    private lateinit var feedbackConfirmationGroup: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dialog_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val messageEditText = view.findViewById<EditText>(R.id.feedback_message)
        val submitButton = view.findViewById<View>(R.id.submit_button)
        enterFeedbackGroup = view.findViewById(R.id.enter_feedback_group)
        feedbackConfirmationGroup = view.findViewById(R.id.feedback_confirmation_group)

        messageEditText.showKeyboard()

        submitButton.setOnClickListener {
            presenter.submitFeedback(messageEditText.text.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.startPresenting(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.stopPresenting()
        handler.removeCallbacks(dismissRunnable)
    }

    override fun showSuccess() {
        enterFeedbackGroup.visibility = GONE
        feedbackConfirmationGroup.visibility = VISIBLE
        handler.postDelayed(dismissRunnable, DISMISS_DELAY)
    }

    override fun showValidationError() {
        toast(R.string.feedback_validation_error)
    }

    companion object {
        private const val DISMISS_DELAY = 2000L
        const val TAG = "FeedbackDialogFragment"

        fun newInstance(): FeedbackDialogFragment {
            return FeedbackDialogFragment()
        }
    }
}
