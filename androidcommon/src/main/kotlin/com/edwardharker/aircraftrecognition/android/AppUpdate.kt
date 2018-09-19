package com.edwardharker.aircraftrecognition.android

import android.content.pm.PackageInfo

class AppUpdate(private val sharedPreferences: AircraftSharedPreferences,
                private val packageInfo: PackageInfo) {
    private val hasUpdated: Boolean by lazy {
        val hasUpdated =
                sharedPreferences.getInt(LAST_APP_VERSION_KEY, 0) < packageInfo.versionCode
        if (hasUpdated) {
            sharedPreferences.saveInt(LAST_APP_VERSION_KEY, packageInfo.versionCode)
        }
        return@lazy hasUpdated
    }

    /**
     * returns true the first time the app runs after an update
     */
    fun hasUpdated(): Boolean = hasUpdated

    companion object {
        const val LAST_APP_VERSION_KEY = "LAST_APP_VERSION"
    }
}
