package com.edwardharker.aircraftrecognition.ui.filter.results

import android.content.Context
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED
import android.support.design.widget.BottomSheetBehavior.STATE_DRAGGING
import android.support.design.widget.BottomSheetBehavior.STATE_HIDDEN
import android.support.design.widget.BottomSheetBehavior.STATE_SETTLING
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.edwardharker.aircraftrecognition.R
import com.edwardharker.aircraftrecognition.filter.results.FilterResultsView
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.model.displayName
import com.edwardharker.aircraftrecognition.ui.AspectRatioImageView
import com.edwardharker.aircraftrecognition.ui.aircraftdetail.launchAircraftDetailActivity
import com.edwardharker.aircraftrecognition.ui.loadAircraftImage
import com.edwardharker.aircraftrecognition.ui.navigator

class FilterResultsRecyclerView : RecyclerView, FilterResultsView {
    private val bottomSheetBehaviour by lazy {
        BottomSheetBehavior.from(this)
    }

    private val pickerHeight by lazy {
        resources.getDimensionPixelSize(R.dimen.filter_picker_height)
    }

    private val adapter = Adapter()
    private val presenter by lazy {
        filterResultsPresenter()
    }

    var hiddenListener: HiddenListener? = null

    var isHidden = false
        private set(value) {
            field = value
            hiddenListener?.onBottomSheetHiddenChanged(isHidden)
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {
        val spanCount = context.resources.getInteger(R.integer.filter_results_column_count)
        layoutManager = StaggeredGridLayoutManager(spanCount, VERTICAL)
        setAdapter(adapter)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bottomSheetBehaviour.peekHeight = h - pickerHeight
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        bottomSheetBehaviour.isHideable = true
        bottomSheetBehaviour.setBottomSheetCallback(BottomSheetCallback())
        updateBottomSheet(bottomSheetBehaviour.state == STATE_HIDDEN)
        presenter.startPresenting(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.stopPresenting()
    }

    override fun showAircraft(aircraft: List<Aircraft>) {
        adapter.update(aircraft)
    }

    fun hideBottomSheet() {
        updateBottomSheet(true)
    }

    fun showBottomSheet() {
        updateBottomSheet(false)
    }

    private fun updateBottomSheet(hide: Boolean) {
        isHidden = hide
        bottomSheetBehaviour.state = if (hide) {
            STATE_HIDDEN
        } else {
            STATE_COLLAPSED
        }
    }

    private inner class BottomSheetCallback : BottomSheetBehavior.BottomSheetCallback() {

        override fun onSlide(bottomSheet: View, slideOffset: Float) {

        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == STATE_DRAGGING || newState == STATE_SETTLING) {
                return
            }
            val newIsHidden = newState == STATE_HIDDEN
            if (isHidden != newIsHidden) {
                isHidden = newIsHidden
            }
        }
    }

    private class Adapter : RecyclerView.Adapter<ViewHolder>() {

        val filterResults = ArrayList<Aircraft>()

        init {
            setHasStableIds(true)
        }

        fun update(aircraft: List<Aircraft>) {
            this.filterResults.clear()
            this.filterResults.addAll(aircraft)
            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val aircraft = filterResults[position]
            holder.aircraft = aircraft

            holder.aircraftName.text = aircraft.displayName
            holder.aircraftImage.loadAircraftImage(aircraft)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_filter_results_aircraft, parent, false)
            )

        override fun getItemCount(): Int = filterResults.size

        override fun getItemId(position: Int): Long = filterResults[position].hashCode().toLong()

    }

    private class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var aircraft: Aircraft? = null
        val aircraftImage: AspectRatioImageView by lazy {
            view.findViewById<AspectRatioImageView>(R.id.aircraft_image)
        }
        val aircraftName: TextView by lazy {
            view.findViewById<TextView>(R.id.aircraft_name)
        }
        val background: View by lazy {
            view.findViewById<View>(R.id.background)
        }

        init {
            view.setOnClickListener {
                aircraft?.let {
                    val activity = view.context as? FragmentActivity
                    activity?.navigator?.launchAircraftDetailActivity(
                        it.id,
                        aircraftImage,
                        background,
                        aircraftName
                    )
                }
            }
        }
    }

    interface HiddenListener {
        fun onBottomSheetHiddenChanged(hidden: Boolean)
    }
}
