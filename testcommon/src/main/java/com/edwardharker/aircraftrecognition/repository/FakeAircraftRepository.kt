package com.edwardharker.aircraftrecognition.repository

import com.edwardharker.aircraftrecognition.model.Aircraft
import rx.Completable
import rx.Observable
import java.util.*
import java.util.Collections.emptyList

class FakeAircraftRepository : AircraftRepository {
    private var aircraft: List<Aircraft> = emptyList()

    private var filterMap: MutableMap<Map<String, String>, List<Aircraft>> = HashMap()
    private var aircraftByIdMap: MutableMap<String, Aircraft> = HashMap()
    private var countByFilterMap: MutableMap<Map<String, String>, Long> = HashMap()

    fun thatEmits(aircraft: List<Aircraft>): FakeAircraftRepository {
        this.aircraft = aircraft
        return this
    }

    fun thatEmits(aircraft: List<Aircraft>, forFilter: Map<String, String>): FakeAircraftRepository {
        filterMap.put(forFilter, aircraft)
        return this
    }

    fun thatEmits(aircraft: Aircraft, forId: String): FakeAircraftRepository {
        aircraftByIdMap.put(forId, aircraft)
        return this
    }

    fun thatReturns(count: Long, forFilter: Map<String, String>): FakeAircraftRepository {
        countByFilterMap.put(forFilter, count)
        return this
    }

    override fun saveAircraft(aircraft: List<Aircraft>) = Unit

    override fun allAircraft(): Observable<List<Aircraft>> = Observable.fromCallable { aircraft }

    override fun allAircraftCount(): Observable<Long> = Observable.just(0)

    override fun filteredAircraft(filters: Map<String, String>): Observable<List<Aircraft>> =
            Observable.fromCallable { filterMap[filters] }

    override fun filteredAircraftCount(filters: Map<String, String>): Long = countByFilterMap[filters] ?: 0

    override fun deleteAllAircraft(): Completable = Completable.error { Throwable() }

    override fun findAircraftById(id: String): Observable<Aircraft> =
            Observable.fromCallable { aircraftByIdMap[id] }
}