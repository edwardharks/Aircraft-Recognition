package com.edwardharker.aircraftrecognition.ui.filter

import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.edwardharker.aircraftrecognition.R
import com.edwardharker.aircraftrecognition.analytics.eventAnalytics
import com.edwardharker.aircraftrecognition.analytics.filterScreen
import com.edwardharker.aircraftrecognition.filter.picker.FilterPickerResetView
import com.edwardharker.aircraftrecognition.maths.mapFromPercent
import com.edwardharker.aircraftrecognition.maths.mapToPercent
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

    private val dragHandleToArrowAnim by lazy {
        AnimatedVectorDrawableCompat.create(this, R.drawable.ic_drag_handle_to_arrow_anim)
    }
    private val arrowToDragHandleAnim by lazy {
        AnimatedVectorDrawableCompat.create(this, R.drawable.ic_arrow_to_drag_handle_anim)
    }

    private val pickerHeight by lazy {
        resources.getDimensionPixelSize(R.dimen.filter_picker_height).toFloat()
    }
    private val rootView by lazy { findViewById<View>(R.id.root_view) }
    private val rootViewBottom: Float
        get() = rootView.bottom.toFloat()
    private val resultHandleHeightVisible by lazy {
        resources.getDimensionPixelSize(R.dimen.filter_results_handle_height_visible).toFloat()
    }
    private val resultHandleHeightHidden by lazy {
        resources.getDimensionPixelSize(R.dimen.filter_results_handle_height_hidden).toFloat()
    }

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
            updateResultsHandle()
            updateFilterPickerClipRect()
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
            updateBottomSheetToggle(hidden = false)
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
        return when {
            item.itemId == R.id.action_search -> {
                navigator.launchSearchActivity()
                true
            }
            item.itemId == R.id.action_reset -> {
                filterPicker.clearFilters()
                filterPickerResetPresenter.resetFilters()
                true
            }
            else -> false
        }
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
            if (resultsHandleView.drawable != dragHandleToArrowAnim) {
                resultsHandleView.setImageDrawable(dragHandleToArrowAnim)
                dragHandleToArrowAnim?.start()
            }
        } else {
            if (resultsHandleView.drawable != arrowToDragHandleAnim) {
                resultsHandleView.setImageDrawable(arrowToDragHandleAnim)
                arrowToDragHandleAnim?.start()
            }
        }
    }

    private fun updateFilterPickerClipRect() {
        filterPicker.clipBounds = Rect(
            filterPicker.left,
            filterPicker.top,
            filterPicker.right,
            bottomSheetView.top
        )
    }

    private fun updateResultsHandle() {
        resultsHandleContainer.translationY = bottomSheetView.y - resultsHandleContainer.height
        if (rootViewBottom > 0) {
            val percent = mapToPercent(
                resultsHandleContainer.translationY,
                pickerHeight,
                rootViewBottom
            )
            val handleContainerHeight = mapFromPercent(
                percent,
                resultHandleHeightVisible,
                resultHandleHeightHidden
            ).toInt()
            if (handleContainerHeight != resultsHandleContainer.height) {
                resultsHandleContainer.layoutParams.height = handleContainerHeight
                resultsHandleContainer.requestLayout()
            }
        }
    }
}

