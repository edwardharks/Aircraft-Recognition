package com.edwardharker.aircraftrecognition.ui.feedback

import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.edwardharker.aircraftrecognition.feedback.FeedbackView
import com.edwardharker.aircraftrecognition.feedback.feedbackPresenter
import com.edwardharker.aircraftrecognition.ui.Navigator

fun Navigator.launchFeedbackDialog() {
    val fragmentManager = activity.supportFragmentManager
    val fragment = FeedbackDialogFragment.newInstance()
    fragment.show(fragmentManager, FeedbackDialogFragment.TAG)
}

class FeedbackDialogFragment : AppCompatDialogFragment(), FeedbackView {
    private val presenter = feedbackPresenter()

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
    }

    override fun showSuccess() {
        Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()
        dismiss()
    }

    companion object {
        const val TAG = "FeedbackDialogFragment"

        fun newInstance(): FeedbackDialogFragment {
            return FeedbackDialogFragment()
        }
    }
}