package com.edwardharker.aircraftrecognition.repository.assets

import android.content.Context
import com.edwardharker.aircraftrecognition.androidcommon.R
import com.edwardharker.aircraftrecognition.gson.aircraftListType
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import com.google.gson.Gson
import rx.Completable
import rx.Observable

class AssetsAircraftRepository(private val context: Context, private val gson: Gson) : AircraftRepository {
    override fun saveAircraft(aircraft: List<Aircraft>) {
        throw UnsupportedOperationException("AssetsAircraftRepository does not support saving aircraft")
    }

    override fun allAircraft(): Observable<List<Aircraft>> =
            Observable.fromCallable {
                val inputStream = context.resources.openRawResource(R.raw.aircraft)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                val aircraft: List<Aircraft> = gson.fromJson(String(buffer), aircraftListType)
                return@fromCallable aircraft
            }

    override fun filteredAircraft(filters: Map<String, String>): Observable<List<Aircraft>> {
        throw UnsupportedOperationException("AssetsAircraftRepository does not support filtered aircraft")
    }

    override fun filteredAircraftCount(filters: Map<String, String>): Long {
        throw UnsupportedOperationException("AssetsAircraftRepository does not support aircraft count")
    }

    override fun deleteAllAircraft(): Completable {
        throw UnsupportedOperationException("AssetsAircraftRepository does not support deleting aircraft")
    }

    override fun findAircraftById(id: String): Observable<Aircraft> {
        throw UnsupportedOperationException("AssetsAircraftRepository does not support finding aircraft")
    }
}