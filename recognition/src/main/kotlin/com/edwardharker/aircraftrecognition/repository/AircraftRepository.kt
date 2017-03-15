package com.edwardharker.aircraftrecognition.repository

import com.edwardharker.aircraftrecognition.model.Aircraft
import rx.Completable
import rx.Observable

interface AircraftRepository {

    fun saveAircraft(aircraft: List<Aircraft>)

    fun allAircraft(): Observable<List<Aircraft>>

    fun filteredAircraft(filters: Map<String, String>): Observable<List<Aircraft>>

    fun filteredAircraftCount(filters: Map<String, String>): Long

    fun deleteAllAircraft(): Completable

    fun findAircraftById(id: String): Observable<Aircraft>
}