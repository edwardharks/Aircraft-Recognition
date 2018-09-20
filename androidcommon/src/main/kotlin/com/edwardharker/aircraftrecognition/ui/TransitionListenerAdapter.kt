package com.edwardharker.aircraftrecognition.ui

import android.transition.Transition

open class TransitionListenerAdapter : Transition.TransitionListener {
    override fun onTransitionEnd(transition: Transition?) = Unit

    override fun onTransitionResume(transition: Transition?) = Unit

    override fun onTransitionPause(transition: Transition?) = Unit

    override fun onTransitionCancel(transition: Transition?) = Unit

    override fun onTransitionStart(transition: Transition?) = Unit
}
