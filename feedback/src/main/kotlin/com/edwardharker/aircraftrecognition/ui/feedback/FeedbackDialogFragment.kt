package com.edwardharker.aircraftrecognition.ui.feedback

import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edwardharker.aircraftrecognition.ui.Navigator

fun Navigator.launchFeedbackDialog() {
    val fragmentManager = activity.supportFragmentManager
    val fragment = FeedbackDialogFragment.newInstance()
    fragment.show(fragmentManager, FeedbackDialogFragment.TAG)
}

class FeedbackDialogFragment : AppCompatDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dialog_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        const val TAG = "FeedbackDialogFragment"
        fun newInstance(): FeedbackDialogFragment {
            return FeedbackDialogFragment()
        }
    }
}