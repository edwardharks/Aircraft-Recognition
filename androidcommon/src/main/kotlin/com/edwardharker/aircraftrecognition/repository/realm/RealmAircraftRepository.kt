package com.edwardharker.aircraftrecognition.repository.realm

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import com.edwardharker.aircraftrecognition.repository.realm.mapper.aircraftToRealmAircraft
import com.edwardharker.aircraftrecognition.repository.realm.mapper.createFilterOptionId
import com.edwardharker.aircraftrecognition.repository.realm.mapper.realmAircraftToAircraft
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmAircraft
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.Sort
import rx.Completable
import rx.Observable

class RealmAircraftRepository(private val realm: Observable<Realm>) : AircraftRepository {
    override fun saveAircraft(aircraft: List<Aircraft>) {
        realm.first().subscribe {
            it.beginTransaction()
            it.copyToRealmOrUpdate(aircraft.map(::aircraftToRealmAircraft))
            it.commitTransaction()
        }
    }

    override fun allAircraft(): Observable<List<Aircraft>> {
        return realm.flatMap {
            it.where(RealmAircraft::class.java)
                .findAllSortedAsync(
                    arrayOf("name"),
                    arrayOf(Sort.ASCENDING)
                )
                .asObservable()
        }.filter {
            it.isLoaded
        }.map {
            it.map(::realmAircraftToAircraft)
        }
    }

    override fun allAircraftCount(): Observable<Long> =
        realm.flatMap { realm ->
            Observable.fromCallable {
                realm.where(RealmAircraft::class.java)
                    .count()
            }
        }

    override fun filteredAircraft(filters: Map<String, String>): Observable<List<Aircraft>> {
        return realm.flatMap {
            it.where(RealmAircraft::class.java)
                .withFilters(filters)
                .findAllSortedAsync(
                    arrayOf("name"),
                    arrayOf(Sort.ASCENDING)
                )
                .asObservable()
        }.filter {
            it.isLoaded
        }.map {
            it.map(::realmAircraftToAircraft)
        }
    }

    override fun filteredAircraftCount(filters: Map<String, String>): Long {
        return realm.toBlocking().first()
            .where(RealmAircraft::class.java)
            .withFilters(filters)
            .count()
    }


    private fun RealmQuery<RealmAircraft>.withFilters(
        filters: Map<String, String>
    ): RealmQuery<RealmAircraft> {
        filters.forEach {
            equalTo("aircraftFilterOptions.id", createFilterOptionId(it.key, it.value))
        }
        return this
    }

    override fun deleteAllAircraft(): Completable {
        return realm.first()
            .doOnNext {
                it.beginTransaction()
                it.deleteAll()
                it.commitTransaction()
            }.toCompletable()
    }

    override fun findAircraftById(id: String): Observable<Aircraft> {
        return realm.flatMap {
            it.where(RealmAircraft::class.java)
                .equalTo("id", id)
                .findAll()
                .asObservable()
        }.filter {
            it.isLoaded
        }.filter {
            it.isNotEmpty()
        }.map(RealmResults<RealmAircraft>::first)
            .map(::realmAircraftToAircraft)
    }
}
