package com.edwardharker.aircraftrecognition.repository

import com.edwardharker.aircraftrecognition.repository.assets.assetsAircraftRepository
import com.edwardharker.aircraftrecognition.repository.assets.assetsFilterRepository
import com.edwardharker.aircraftrecognition.repository.realm.realmAircraftRepository
import com.edwardharker.aircraftrecognition.repository.retrofit.retrofitAircraftRepository
import com.edwardharker.aircraftrecognition.repository.retrofit.retrofitFeedbackRepository

fun aircraftRepository(): AircraftRepository {
    return realmAircraftRepository()
}

fun staticAircraftRepository(): AircraftRepository {
    return assetsAircraftRepository()
}

fun remoteAircraftRepository(): AircraftRepository {
    return retrofitAircraftRepository()
}

fun filterRepository(): FilterRepository {
    return assetsFilterRepository()
}

fun feedbackRepository(): FeedbackRepository {
    return retrofitFeedbackRepository()
}
