package com.edwardharker.aircraftrecognition.repository

import com.edwardharker.aircraftrecognition.model.Filter
import rx.Observable

interface FilterRepository {

    fun filters(): Observable<List<Filter>>
}
