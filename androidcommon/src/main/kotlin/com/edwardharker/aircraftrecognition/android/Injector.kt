package com.edwardharker.aircraftrecognition.android

import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.preference.PreferenceManager.getDefaultSharedPreferences
import com.edwardharker.aircraftrecognition.android.preferences.AircraftSharedPreferences
import com.edwardharker.aircraftrecognition.android.preferences.AndroidSharedPreferences
import com.edwardharker.aircraftrecognition.applicationContext

private val appUpdate = AppUpdate(aircraftSharedPreferences(), packageInfo())

fun appUpdate(): AppUpdate {
    return appUpdate
}

fun firstInstall(): FirstInstall {
    return FirstInstall(
        sharedPreferences = aircraftSharedPreferences(),
        packageInfo = packageInfo()
    )
}

fun aircraftSharedPreferences(): AircraftSharedPreferences {
    return AndroidSharedPreferences(
        sharedPreferences()
    )
}

fun sharedPreferences(): SharedPreferences {
    return getDefaultSharedPreferences(applicationContext())
}

fun packageManager(): PackageManager {
    return applicationContext().packageManager
}

fun packageInfo(): PackageInfo {
    return packageManager().getPackageInfo(applicationContext().packageName, 0)
}
