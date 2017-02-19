package com.edwardharker.aircraftrecognition.model

data class Filter(
        val name: String = "",
        val filterText: String = "",
        val filterOptions: List<FilterOption> = emptyList()
)
