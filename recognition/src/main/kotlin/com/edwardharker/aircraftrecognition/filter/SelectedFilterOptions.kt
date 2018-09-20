package com.edwardharker.aircraftrecognition.filter

import com.edwardharker.aircraftrecognition.model.SelectedFilterOptionsMap
import rx.Observable

interface SelectedFilterOptions {

    fun isSelected(name: String, value: String): Boolean

    fun select(name: String, value: String)

    fun deselect(name: String)

    fun asObservable(): Observable<SelectedFilterOptionsMap>
}
