package com.edwardharker.aircraftrecognition.ui.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.model.displayName
import com.edwardharker.aircraftsearch.R

class SearchAdapter(
    private val aircraftClickListener: (Aircraft) -> Unit,
    private val feedbackClickListener: () -> Unit
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private var searchResults: MutableList<Aircraft> = ArrayList()

    fun bindSearchResults(searchResults: List<Aircraft>) {
        this.searchResults.clear()
        this.searchResults.addAll(searchResults)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < searchResults.size) {
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
        val aircraft = searchResults[position]
        holder.label.text = aircraft.displayName
        holder.itemView.setOnClickListener { aircraftClickListener(aircraft) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_search_result, parent, false)
        )

    override fun getItemCount(): Int = searchResults.size + 1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label = view as TextView
    }
}
