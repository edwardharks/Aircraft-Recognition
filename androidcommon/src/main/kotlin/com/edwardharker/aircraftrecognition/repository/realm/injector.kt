package com.edwardharker.aircraftrecognition.repository.realm

import com.edwardharker.aircraftrecognition.android.appUpdate
import com.edwardharker.aircraftrecognition.applicationContext
import io.realm.Realm
import io.realm.RealmConfiguration
import rx.Observable

fun realmAircraftRepository(): RealmAircraftRepository =
    RealmAircraftRepository(RealmInstance.realm)

private object RealmInstance {
    @JvmStatic
    val realm: Observable<Realm>

    init {
        Realm.init(applicationContext())
        if (appUpdate().hasUpdated()) {
            Realm.deleteRealm(RealmConfiguration.Builder().build())
        }
        realm = Realm.getDefaultInstance().asObservable()
    }
}
