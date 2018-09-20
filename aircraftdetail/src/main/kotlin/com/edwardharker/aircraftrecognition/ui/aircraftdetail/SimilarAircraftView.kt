package com.edwardharker.aircraftrecognition.ui.aircraftdetail

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import com.edwardharker.aircraftrecognition.aircraftdetail.R
import com.edwardharker.aircraftrecognition.analytics.similarAircraftClickEvent
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.similaraircraft.SimilarAircraftMvpView
import com.edwardharker.aircraftrecognition.ui.dpToPixels

class SimilarAircraftView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), SimilarAircraftMvpView {

    private val presenter = similarAircraftPresenter()

    private val railView = AircraftRailView(context).apply {
        eventFactory = ::similarAircraftClickEvent
    }

    var aircraftId: String? = null

    init {
        orientation = VERTICAL
        val titleView = TextView(context).apply {
            setTextAppearanceCompat(R.style.TextAppearance_AppCompat_Title)
            text = context.getString(R.string.similar_aircraft)
            setPadding(TITLE_PADDING.dpToPixels(), 0, TITLE_PADDING.dpToPixels(), 0)
        }

        addView(titleView, LayoutParams(MATCH_PARENT, WRAP_CONTENT))
        addView(railView, LayoutParams(MATCH_PARENT, WRAP_CONTENT))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        aircraftId?.let { id -> presenter.startPresenting(this, id) }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.stopPresenting()
    }

    override fun showSimilarAircraft(aircraft: List<Aircraft>) {
        railView.aircraft = aircraft
    }

    override fun hideView() {
        visibility = GONE
    }

    private companion object {
        private const val TITLE_PADDING = 16
    }
}

// TODO move
private fun TextView.setTextAppearanceCompat(textAppearance: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        @Suppress("DEPRECATION")
        setTextAppearance(context, textAppearance)
    } else {
        setTextAppearance(textAppearance)
    }
}
