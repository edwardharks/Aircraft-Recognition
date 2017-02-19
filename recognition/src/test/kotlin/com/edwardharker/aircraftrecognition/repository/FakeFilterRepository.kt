package com.edwardharker.aircraftrecognition.repository

import com.edwardharker.aircraftrecognition.model.Filter
import rx.Observable

class FakeFilterRepository : FilterRepository {
    private var filters: List<Filter> = emptyList()

    fun thatEmits(filters: List<Filter>): FakeFilterRepository {
        this.filters = filters
        return this
    }

    override fun filters(): Observable<List<Filter>> = Observable.fromCallable { filters }
}

