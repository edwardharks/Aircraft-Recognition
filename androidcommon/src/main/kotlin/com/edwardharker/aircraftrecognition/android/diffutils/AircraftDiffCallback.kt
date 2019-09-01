package com.edwardharker.aircraftrecognition.android.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.edwardharker.aircraftrecognition.model.Aircraft

object AircraftDiffCallback : DiffUtil.ItemCallback<Aircraft>() {
    override fun areItemsTheSame(first: Aircraft, second: Aircraft): Boolean {
        return first.id == second.id
    }

    override fun areContentsTheSame(first: Aircraft, second: Aircraft): Boolean {
        return first == second
    }
}
