package com.edwardharker.aircraftrecognition.repository.retrofit

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import rx.Completable
import rx.Observable

class RetrofitAircraftRepository(
        private val aircraftService: AircraftService
) : AircraftRepository {

    override fun saveAircraft(aircraft: List<Aircraft>) {
        throw UnsupportedOperationException()
    }

    override fun allAircraft(): Observable<List<Aircraft>> =
            aircraftService.getAircraft()
                    .flatMap { response ->
                        return@flatMap if (response.isSuccessful) {
                            Observable.just(response.body() ?: listOf())
                        } else {
                            Observable.never<List<Aircraft>>()
                        }
                    }

    override fun allAircraftCount(): Observable<Long> {
        throw UnsupportedOperationException()
    }

    override fun filteredAircraft(filters: Map<String, String>): Observable<List<Aircraft>> {
        throw UnsupportedOperationException()
    }

    override fun filteredAircraftCount(filters: Map<String, String>): Long {
        throw UnsupportedOperationException()
    }

    override fun deleteAllAircraft(): Completable {
        throw UnsupportedOperationException()
    }

    override fun findAircraftById(id: String): Observable<Aircraft> {
        throw UnsupportedOperationException()
    }

}