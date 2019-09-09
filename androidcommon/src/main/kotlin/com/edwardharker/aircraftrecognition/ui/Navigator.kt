package com.edwardharker.aircraftrecognition.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.edwardharker.aircraftrecognition.android.yearclass.YearClassProvider
import com.edwardharker.aircraftrecognition.android.yearclass.yearClassProvider

class Navigator(
    val activity: FragmentActivity,
    private val yearClassProvider: YearClassProvider
) {
    fun launch(intent: Intent, options: Bundle? = null) {
        if (yearClassProvider.yearClass <= 2014) {
            activity.startActivity(intent, null)
        } else {
            intent.putExtra(STARTED_WITH_TRANSITION_EXTRA, true)
            activity.startActivity(intent, options)
        }
    }

    companion object {
        const val STARTED_WITH_TRANSITION_EXTRA = "startedWithTransition"
    }
}

val FragmentActivity.navigator: Navigator
    get() = Navigator(this, yearClassProvider())
