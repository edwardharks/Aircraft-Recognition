package com.edwardharker.aircraftrecognition.repository.realm.mapper

import io.realm.RealmList
import io.realm.RealmObject

inline fun <T, reified R : RealmObject> List<T>.mapToRealmList(transform: (T) -> R): RealmList<R> {
    val realmList = RealmList<R>()
    forEach { realmList.add(transform(it)) }
    return realmList
}

inline fun <T, T2, reified R : RealmObject> Map<T, T2>.mapToRealmList(transform: (Pair<T, T2>) -> R): RealmList<R> {
    val realmList = RealmList<R>()
    forEach { realmList.add(transform(Pair(it.key, it.value))) }
    return realmList
}