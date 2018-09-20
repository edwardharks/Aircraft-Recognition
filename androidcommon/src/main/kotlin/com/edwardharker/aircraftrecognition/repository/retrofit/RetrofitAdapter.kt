package com.edwardharker.aircraftrecognition.repository.retrofit

import com.edwardharker.aircraftrecognition.androidcommon.BuildConfig.AIRCRAFT_BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAdapter {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(AIRCRAFT_BASE_URL)
            .addConverterFactory(UnitConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
    }

    val aircraftService: AircraftService by lazy {
        retrofit.create(AircraftService::class.java)
    }

    val feedbackService: FeedbackService by lazy {
        retrofit.create(FeedbackService::class.java)
    }
}

