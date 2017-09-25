package com.edwardharker.aircraftrecognition.ui.aircraftdetail

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec.*
import android.view.ViewGroup
import android.widget.TextView
import com.edwardharker.aircraftrecognition.aircraftdetail.R
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.model.displayName
import com.edwardharker.aircraftrecognition.ui.*
import java.util.ArrayList

class AircraftRailView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var aircraft: List<Aircraft>
        set(value) = railAdapter.update(value)
        get() = railAdapter.filterResults

    private val railAdapter: RailAdapter
        get() = super.getAdapter() as RailAdapter

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = RailAdapter()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) =
            super.onMeasure(widthSpec, makeMeasureSpec(240.dpToPixels(), EXACTLY))

}

private class RailAdapter : RecyclerView.Adapter<ViewHolder>() {

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
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_aircraft_rail_aircraft, parent, false))

    override fun getItemCount(): Int = filterResults.size

    override fun getItemId(position: Int): Long = filterResults[position].hashCode().toLong()

}

private class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var aircraft: Aircraft? = null
    val aircraftImage by lazy { view.findViewById(R.id.rail_aircraft_image) as AspectRatioImageView }
    val aircraftName by lazy { view.findViewById(R.id.rail_aircraft_name) as TextView }
    val background: View by lazy { view.findViewById(R.id.rail_background) }

    init {
        view.setOnClickListener {
            aircraft?.let {
                val activity = view.context as Activity
                activity.activityLauncher().launchAircraftDetailActivity(it.id, aircraftImage, background, aircraftName)
            }
        }
    }
}
