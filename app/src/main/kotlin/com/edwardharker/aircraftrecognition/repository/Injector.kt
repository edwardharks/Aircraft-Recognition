package com.edwardharker.aircraftrecognition.repository

import com.edwardharker.aircraftrecognition.repository.assets.assetsAircraftRepository
import com.edwardharker.aircraftrecognition.repository.assets.assetsFilterRepository
import com.edwardharker.aircraftrecognition.repository.realm.realmAircraftRepository

fun aircraftRepository(): AircraftRepository = realmAircraftRepository()

fun staticAircraftRepository(): AircraftRepository = assetsAircraftRepository()

fun filterRepository(): FilterRepository = assetsFilterRepository()