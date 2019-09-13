package com.edwardharker.aircraftrecognition.ui.aircraftdetail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec.EXACTLY
import android.view.View.MeasureSpec.makeMeasureSpec
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edwardharker.aircraftrecognition.aircraftdetail.R
import com.edwardharker.aircraftrecognition.analytics.Event
import com.edwardharker.aircraftrecognition.analytics.eventAnalytics
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.ui.AspectRatioImageView
import com.edwardharker.aircraftrecognition.ui.AspectRatioImageView.Aspect.HEIGHT
import com.edwardharker.aircraftrecognition.ui.dpToPixels
import com.edwardharker.aircraftrecognition.ui.loadAircraftImage
import com.edwardharker.aircraftrecognition.ui.navigator
import java.util.*

class AircraftRailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    var aircraft: List<Aircraft>
        set(value) = railAdapter.update(value)
        get() = railAdapter.filterResults

    var eventFactory: (aircraftId: String) -> Event?
        set(value) {
            railAdapter.eventFactory = value
        }
        get() = railAdapter.eventFactory

    private val railAdapter: RailAdapter
        get() = super.getAdapter() as RailAdapter

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = RailAdapter()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, makeMeasureSpec(RAIL_HEIGHT.dpToPixels(), EXACTLY))
    }

    private companion object {
        private const val RAIL_HEIGHT = 200
    }
}

private class RailAdapter : RecyclerView.Adapter<ViewHolder>() {

    val filterResults = ArrayList<Aircraft>()
    var eventFactory: (aircraftId: String) -> Event? = { null }

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

        holder.aircraftName.text = aircraft.name
        holder.aircraftImage.loadAircraftImage(aircraft)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.view_aircraft_rail_aircraft,
                parent,
                false
            ),
            eventFactory = eventFactory
        )

    override fun getItemCount(): Int = filterResults.size

    override fun getItemId(position: Int): Long = filterResults[position].hashCode().toLong()

}

private class ViewHolder(
    val view: View,
    eventFactory: (aircraftId: String) -> Event?
) : RecyclerView.ViewHolder(view) {
    var aircraft: Aircraft? = null

    val aircraftImage: AspectRatioImageView by lazy {
        view.findViewById<AspectRatioImageView>(R.id.rail_aircraft_image)
    }
    val aircraftName: TextView by lazy {
        view.findViewById<TextView>(R.id.rail_aircraft_name)
    }
    val background: View by lazy {
        view.findViewById<View>(R.id.rail_background)
    }

    init {
        aircraftImage.aspect = HEIGHT
    }

    init {
        view.setOnClickListener {
            aircraft?.let { aircraft ->
                val activity = view.context as FragmentActivity
                activity.navigator
                    .launchAircraftDetailActivity(
                        aircraft.id,
                        aircraftImage,
                        background,
                        aircraftName
                    )
                eventFactory.invoke(aircraft.id)?.let { event ->
                    eventAnalytics().logEvent(event)
                }
            }
        }
    }
}
