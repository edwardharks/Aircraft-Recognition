package com.edwardharker.aircraftrecognition.android

import android.content.pm.PackageInfo
import com.edwardharker.aircraftrecognition.android.preferences.AircraftSharedPreferences

class FirstInstall(
    private val sharedPreferences: AircraftSharedPreferences,
    private val packageInfo: PackageInfo
) {
    val firstInstallVersion: Int
        get() {
            saveVersion()
            return sharedPreferences.getInt(FIRST_INSTALL_VERSION_KEY, 0)
        }

    fun saveVersion() {
        if (!sharedPreferences.contains(FIRST_INSTALL_VERSION_KEY)) {
            sharedPreferences.saveInt(FIRST_INSTALL_VERSION_KEY, packageInfo.versionCode)
        }
    }

    private companion object {
        private const val FIRST_INSTALL_VERSION_KEY = "FIRST_INSTALL_VERSION"
    }
}
