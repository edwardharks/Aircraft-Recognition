package com.edwardharker.aircraftrecognition.android

import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.preference.PreferenceManager.*
import com.edwardharker.aircraftrecognition.applicationContext

private val appUpdate = AppUpdate(aircraftSharedPreferences(), packageInfo())

fun appUpdate(): AppUpdate = appUpdate

fun aircraftSharedPreferences(): AircraftSharedPreferences =
        AndroidSharedPreferences(sharedPreferences())

fun sharedPreferences(): SharedPreferences =
        getDefaultSharedPreferences(applicationContext())

fun packageManager(): PackageManager =
        applicationContext().packageManager

fun packageInfo(): PackageInfo =
        packageManager().getPackageInfo(applicationContext().packageName, 0)
