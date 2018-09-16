package com.edwardharker.aircraftrecognition.ui.filter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.support.v7.widget.Toolbar.OnMenuItemClickListener
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.edwardharker.aircraftrecognition.R
import com.edwardharker.aircraftrecognition.analytics.eventAnalytics
import com.edwardharker.aircraftrecognition.analytics.filterScreen
import com.edwardharker.aircraftrecognition.ui.navigator
import com.edwardharker.aircraftrecognition.ui.bind
import com.edwardharker.aircraftrecognition.ui.filter.picker.FilterPickerRecyclerView
import com.edwardharker.aircraftrecognition.ui.filter.results.FilterResultsRecyclerView
import com.edwardharker.aircraftrecognition.ui.search.launchSearchActivity

class FilterActivity : AppCompatActivity(), OnMenuItemClickListener,
    FilterResultsRecyclerView.HiddenListener {
    private val toolbar by bind<Toolbar>(R.id.toolbar)
    private val bottomSheetView by bind<FilterResultsRecyclerView>(R.id.content_filter)
    private val filterPicker by bind<FilterPickerRecyclerView>(R.id.filter_picker)
    private val resultsHandleView by bind<ImageView>(R.id.results_handle)
    private val resultsHandleContainer by bind<View>(R.id.results_handle_container)

    private val expandLessDrawable by lazy { getDrawable(R.drawable.ic_expand_less_white_24dp) }
    private val expandMoreDrawable by lazy { getDrawable(R.drawable.ic_expand_more_white_24dp) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        toolbar.inflateMenu(R.menu.menu_filter)
        toolbar.setOnMenuItemClickListener(this)

        bottomSheetView.hiddenListener = this

        bottomSheetView.viewTreeObserver.addOnPreDrawListener {
            resultsHandleContainer.translationY = bottomSheetView.y - resultsHandleView.height
            return@addOnPreDrawListener true
        }

        resultsHandleContainer.setOnClickListener { toggleBottomSheetHidden() }
        resultsHandleContainer.setOnTouchListener { _, event ->
            bottomSheetView.onTouchEvent(event)
            return@setOnTouchListener false
        }
    }

    override fun onStart() {
        super.onStart()
        eventAnalytics().logScreenView(filterScreen())
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

    private fun updateBottomSheetToggle(hidden: Boolean) {
        if (hidden) {
            resultsHandleView.setImageDrawable(expandLessDrawable)
        } else {
            resultsHandleView.setImageDrawable(expandMoreDrawable)
        }
    }
}

