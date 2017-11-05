package com.edwardharker.aircraftrecognition.repository.retrofit

private fun aircraftService(): AircraftService = RetrofitAdapter.aircraftService

fun retrofitAircraftRepository(): RetrofitAircraftRepository =
        RetrofitAircraftRepository(aircraftService = aircraftService())

