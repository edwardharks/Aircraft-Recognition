package com.edwardharker.aircraftrecognition.filter

import com.edwardharker.aircraftrecognition.model.SelectedFilterOptionsMap
import rx.Observable
import rx.lang.kotlin.BehaviorSubject
import java.util.*

class InMemorySelectedFilterOptions : SelectedFilterOptions {

    private val selections = HashMap<String, String>()
    private val subject = BehaviorSubject<Map<String, String>>(emptyMap())

    override fun isSelected(name: String, value: String): Boolean
            = selections.containsKey(name) && selections[name].equals(value)

    override fun select(name: String, value: String) {
        selections.put(name, value)
        subject.onNext(HashMap<String, String>(selections))
    }

    override fun deselect(name: String) {
        selections.remove(name)
        subject.onNext(HashMap<String, String>(selections))
    }

    override fun asObservable(): Observable<SelectedFilterOptionsMap> = subject
}
