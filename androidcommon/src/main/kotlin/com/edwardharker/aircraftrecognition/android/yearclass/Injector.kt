package com.edwardharker.aircraftrecognition.android.yearclass

import android.annotation.SuppressLint
import com.edwardharker.aircraftrecognition.applicationContext

@SuppressLint("StaticFieldLeak")
private val yearClassProvider = YearClassProvider(applicationContext())

fun yearClassProvider(): YearClassProvider {
    return yearClassProvider
}
