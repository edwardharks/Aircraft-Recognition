package com.edwardharker.aircraftrecognition.filter

import com.edwardharker.aircraftrecognition.model.SelectedFilterOptionsMap
import rx.Observable
import rx.lang.kotlin.PublishSubject

class FakeSelectedFilterOptions : SelectedFilterOptions {

    val subject = PublishSubject<SelectedFilterOptionsMap>()

    override fun isSelected(name: String, value: String): Boolean = false

    override fun select(name: String, value: String) = Unit

    override fun deselect(name: String) = Unit

    override fun asObservable(): Observable<SelectedFilterOptionsMap> = subject
}