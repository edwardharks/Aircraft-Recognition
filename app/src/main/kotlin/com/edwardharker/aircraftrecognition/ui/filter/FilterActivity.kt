package com.edwardharker.aircraftrecognition.ui.filter

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.edwardharker.aircraftrecognition.R
import com.edwardharker.aircraftrecognition.analytics.eventAnalytics
import com.edwardharker.aircraftrecognition.analytics.filterScreen
import com.edwardharker.aircraftrecognition.filter.picker.FilterPickerResetView
import com.edwardharker.aircraftrecognition.perf.TracerFactory.filterActivityContentLoadTracer
import com.edwardharker.aircraftrecognition.perf.TracerFactory.filterPickerLoadTracer
import com.edwardharker.aircraftrecognition.ui.bind
import com.edwardharker.aircraftrecognition.ui.filter.picker.FilterPickerRecyclerView
import com.edwardharker.aircraftrecognition.ui.filter.picker.filterPickerResetPresenter
import com.edwardharker.aircraftrecognition.ui.filter.results.FilterResultsRecyclerView
import com.edwardharker.aircraftrecognition.ui.navigator
import com.edwardharker.aircraftrecognition.ui.search.launchSearchActivity

class FilterActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener,
    FilterResultsRecyclerView.HiddenListener, FilterPickerResetView {
    private val toolbar by bind<Toolbar>(R.id.toolbar)
    private val bottomSheetView by bind<FilterResultsRecyclerView>(R.id.content_filter)
    private val filterPicker by bind<FilterPickerRecyclerView>(R.id.filter_picker)
    private val resultsHandleView by bind<ImageView>(R.id.results_handle)
    private val resultsHandleContainer by bind<View>(R.id.results_handle_container)

    private val expandLessDrawable by lazy { getDrawable(R.drawable.ic_expand_less_white_24dp) }
    private val expandMoreDrawable by lazy { getDrawable(R.drawable.ic_expand_more_white_24dp) }

    private val filterActivityContentLoadTracer = filterActivityContentLoadTracer()
    private val filterPickerLoadTracer = filterPickerLoadTracer()

    private val filterPickerResetPresenter = filterPickerResetPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        filterActivityContentLoadTracer.start()
        filterPickerLoadTracer.start()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        toolbar.inflateMenu(R.menu.menu_filter_search)
        toolbar.setOnMenuItemClickListener(this)

        bottomSheetView.hiddenListener = this

        bottomSheetView.viewTreeObserver.addOnPreDrawListener {
            resultsHandleContainer.translationY = bottomSheetView.y - resultsHandleView.height
            return@addOnPreDrawListener true
        }

        bottomSheetView.showAircraftListener = {
            // clear the listener so we don't get called again
            bottomSheetView.showAircraftListener = null
            filterActivityContentLoadTracer.stop()
        }
        filterPicker.showFilterListener = {
            // clear the listener so we don't get called again
            filterPicker.showFilterListener = null
            filterPickerLoadTracer.stop()
        }

        resultsHandleContainer.setOnClickListener { toggleBottomSheetHidden() }
        resultsHandleContainer.setOnTouchListener { _, event ->
            bottomSheetView.onTouchEvent(event)
            return@setOnTouchListener false
        }
    }

    override fun onStart() {
        super.onStart()
        filterPickerResetPresenter.startPresenting(this)
        eventAnalytics().logScreenView(filterScreen())
    }

    override fun onStop() {
        super.onStop()
        filterPickerResetPresenter.stopPresenting()
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            navigator.launchSearchActivity()
        }
        return false
    }

    override fun onBackPressed() {
        if (bottomSheetView.isHidden) {
            bottomSheetView.showBottomSheet()
        } else {
            super.onBackPressed()
        }
    }

    override fun onBottomSheetHiddenChanged(hidden: Boolean) {
        updateBottomSheetToggle(hidden)
        filterPicker.scrollOnSelection = !hidden
    }

    private fun toggleBottomSheetHidden() {
        if (bottomSheetView.isHidden) {
            bottomSheetView.showBottomSheet()
        } else {
            bottomSheetView.hideBottomSheet()
        }
    }

    override fun showReset() {
        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.menu_filter_reset)
    }

    override fun hideReset() {
        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.menu_filter_search)
    }

    private fun updateBottomSheetToggle(hidden: Boolean) {
        if (hidden) {
            resultsHandleView.setImageDrawable(expandLessDrawable)
        } else {
            resultsHandleView.setImageDrawable(expandMoreDrawable)
        }
    }
}

