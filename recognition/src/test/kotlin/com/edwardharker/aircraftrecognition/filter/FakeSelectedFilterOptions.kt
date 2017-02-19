package com.edwardharker.aircraftrecognition.filter

import rx.Observable
import rx.lang.kotlin.PublishSubject

class FakeSelectedFilterOptions : SelectedFilterOptions {

    val subject = PublishSubject<Map<String, String>>()

    override fun isSelected(name: String, value: String): Boolean = false

    override fun select(name: String, value: String) {
    }

    override fun deselect(name: String) {
    }

    override fun asObservable(): Observable<Map<String, String>> = subject
}