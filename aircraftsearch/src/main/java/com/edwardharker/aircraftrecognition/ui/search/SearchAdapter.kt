package com.edwardharker.aircraftrecognition.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edwardharker.aircraftrecognition.android.diffutils.AircraftDiffCallback
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftsearch.R

class SearchAdapter(
    private val aircraftClickListener: (Aircraft) -> Unit,
    private val feedbackClickListener: () -> Unit
) : ListAdapter<Aircraft, SearchAdapter.ViewHolder>(AircraftDiffCallback) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < super.getItemCount()) {
            bindAircraft(position, holder)
        } else {
            bindFeedback(holder)
        }
    }

    private fun bindFeedback(holder: ViewHolder) {
        holder.label.text = holder.itemView.context.getString(R.string.suggest_aircraft)
        holder.itemView.setOnClickListener { feedbackClickListener() }
    }

    private fun bindAircraft(
        position: Int,
        holder: ViewHolder
    ) {
        val aircraft = getItem(position)
        holder.label.text = aircraft.name
        holder.itemView.setOnClickListener { aircraftClickListener(aircraft) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_search_result, parent, false)
        )
    }

    override fun getItemCount(): Int = super.getItemCount() + 1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label = view as TextView
    }
}
