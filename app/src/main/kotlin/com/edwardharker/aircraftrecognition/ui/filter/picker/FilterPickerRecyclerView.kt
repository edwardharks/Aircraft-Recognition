package com.edwardharker.aircraftrecognition.ui.filter.picker

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edwardharker.aircraftrecognition.R
import com.edwardharker.aircraftrecognition.analytics.Events.selectFilterOptionEvent
import com.edwardharker.aircraftrecognition.analytics.eventAnalytics
import com.edwardharker.aircraftrecognition.filter.picker.FilterPickerView
import com.edwardharker.aircraftrecognition.model.Filter
import com.edwardharker.aircraftrecognition.ui.SlowScrollingLinearLayoutManager
import java.lang.Math.abs
import java.lang.Math.min
import java.util.*

class FilterPickerRecyclerView : RecyclerView, FilterPickerView {
    private val pickerHeight by lazy {
        resources.getDimensionPixelSize(R.dimen.filter_picker_height)
    }

    private val adapter = Adapter()
    private val linearLayoutManager: LinearLayoutManager by lazy {
        SlowScrollingLinearLayoutManager(context)
    }
    private val presenter = filterPickerPresenter()
    private val eventAnalytics = eventAnalytics()

    var scrollOnSelection = true
    var showFilterListener: (() -> Unit)? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {
        clipToPadding = false
        layoutManager = linearLayoutManager
        setAdapter(adapter)
        addOnScrollListener(ScrollListener())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setPadding(paddingLeft, paddingTop, paddingRight, h - pickerHeight)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.startPresenting(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.stopPresenting()
    }

    override fun showFilters(filters: List<Filter>) {
        val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
        val currentFilter = if (firstVisibleItemPosition >= 0) {
            adapter.filters[firstVisibleItemPosition]
        } else null

        adapter.update(filters)
        val newPosition = adapter.filters.indexOf(currentFilter)
        if (newPosition >= 0) {
            scrollToPosition(newPosition)
        }

        showFilterListener?.invoke()
    }

    fun clearFilters() {
        adapter.update(emptyList())
    }

    private fun snap() {
        val position = linearLayoutManager.findFirstVisibleItemPosition()
        if (position >= 0) {
            val view = linearLayoutManager.findViewByPosition(position)!!
            val scrollPos = if (abs(view.top) > view.height / 2) {
                min(adapter.itemCount, position + 1)
            } else {
                position
            }
            smoothScrollToPosition(scrollPos)
        }
    }

    private fun onFilterOptionSelected() {
        handler.postDelayed({
            val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
            if (scrollOnSelection && firstVisibleItemPosition < linearLayoutManager.itemCount - 1) {
                smoothScrollToPosition(firstVisibleItemPosition + 1)
            }
        }, AUTO_SCROLL_DELAY)
    }

    private inner class ScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == SCROLL_STATE_IDLE) {
                snap()
            }
        }
    }

    private inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        private val _filters = ArrayList<Filter>()
        val filters: List<Filter> = _filters

        fun update(filters: List<Filter>) {
            _filters.clear()
            _filters.addAll(filters)
            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val filter = filters[position]
            holder.view.bindTo(filter)
            holder.view.selectionListener = { selectedFilter, selectedFilterOption ->
                onFilterOptionSelected()
                eventAnalytics.logEvent(
                    selectFilterOptionEvent(
                        filter = selectedFilter,
                        filterOption = selectedFilterOption
                    )
                )
            }
        }

        override fun getItemCount(): Int = filters.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(FilterPicker(parent.context))
    }

    private inner class ViewHolder(val view: FilterPicker) : RecyclerView.ViewHolder(view) {
        init {
            view.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }
    }

    private companion object {
        private const val AUTO_SCROLL_DELAY = 200L
    }
}
