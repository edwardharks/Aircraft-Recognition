package com.edwardharker.aircraftrecognition.repository.realm

import io.realm.Realm
import rx.Observable

fun realmAircraftRepository(): RealmAircraftRepository = RealmAircraftRepository(RealmInstance.realm)

private object RealmInstance {
    @JvmStatic val realm: Observable<Realm> = Realm.getDefaultInstance().asObservable()
}