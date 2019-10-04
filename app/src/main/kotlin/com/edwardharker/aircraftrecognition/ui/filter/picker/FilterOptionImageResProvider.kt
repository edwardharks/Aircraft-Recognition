package com.edwardharker.aircraftrecognition.ui.filter.picker

import com.edwardharker.aircraftrecognition.R
import com.edwardharker.aircraftrecognition.model.FilterOption

val FilterOption.imageRes: Int?
    get() {
        return when (this.image) {
            "filter_propeller" -> R.drawable.ic_filter_propeller
            "filter_jet_1" -> R.drawable.ic_filter_jet_1
            "filter_jet_2" -> R.drawable.ic_filter_jet_2
            "filter_jet_3" -> R.drawable.ic_filter_jet_3
            "filter_jet_4" -> R.drawable.ic_filter_jet_4
            "filter_jet_6" -> R.drawable.ic_filter_jet_6
            "filter_under_wing" -> R.drawable.ic_filter_under_wing
            "filter_tail" -> R.drawable.ic_filter_tail
            "filter_wings_tail" -> R.drawable.ic_filter_wings_tail
            "filter_nose" -> R.drawable.ic_filter_nose
            "filter_over_fuselage" -> R.drawable.ic_filter_over_fuselage
            "filter_under_fuselage" -> R.drawable.ic_filter_under_fuselage
            "filter_single_deck" -> R.drawable.ic_filter_single_deck
            "filter_double_deck" -> R.drawable.ic_filter_double_deck
            else -> null
        }
    }
