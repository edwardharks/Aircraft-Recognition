package com.edwardharker.aircraftrecognition.repository

import com.edwardharker.aircraftrecognition.repository.assets.assetsAircraftRepository
import com.edwardharker.aircraftrecognition.repository.assets.assetsFilterRepository
import com.edwardharker.aircraftrecognition.repository.realm.realmAircraftRepository
import com.edwardharker.aircraftrecognition.repository.retrofit.retrofitAircraftRepository
import com.edwardharker.aircraftrecognition.repository.retrofit.retrofitFeedbackRepository

fun aircraftRepository(): AircraftRepository = realmAircraftRepository()

fun staticAircraftRepository(): AircraftRepository = assetsAircraftRepository()

fun remoteAircraftRepository(): AircraftRepository = retrofitAircraftRepository()

fun filterRepository(): FilterRepository = assetsFilterRepository()

fun feedbackRepository(): FeedbackRepository = retrofitFeedbackRepository()