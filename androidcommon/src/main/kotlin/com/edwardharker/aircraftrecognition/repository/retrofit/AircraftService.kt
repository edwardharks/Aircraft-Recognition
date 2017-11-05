package com.edwardharker.aircraftrecognition.repository.retrofit

import com.edwardharker.aircraftrecognition.model.Aircraft
import retrofit2.Response
import retrofit2.http.GET
import rx.Observable


interface AircraftService {
    @GET("aircraft/aircraft.json")
    fun getAircraft(): Observable<Response<List<Aircraft>>>
}
