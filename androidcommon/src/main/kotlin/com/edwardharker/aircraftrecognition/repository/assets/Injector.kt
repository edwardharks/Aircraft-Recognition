package com.edwardharker.aircraftrecognition.repository.assets

import com.edwardharker.aircraftrecognition.applicationContext
import com.edwardharker.aircraftrecognition.gson.gson

fun assetsAircraftRepository(): AssetsAircraftRepository = AssetsAircraftRepository(applicationContext(), gson())

fun assetsFilterRepository(): AssetsFilterRepository = AssetsFilterRepository(applicationContext(), gson())