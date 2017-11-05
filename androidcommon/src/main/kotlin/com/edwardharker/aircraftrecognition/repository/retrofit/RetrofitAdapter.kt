package com.edwardharker.aircraftrecognition.repository.retrofit

import com.edwardharker.aircraftrecognition.androidcommon.BuildConfig.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory


object RetrofitAdapter {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(AIRCRAFT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }

    val aircraftService: AircraftService by lazy {
        retrofit.create(AircraftService::class.java)
    }
}
