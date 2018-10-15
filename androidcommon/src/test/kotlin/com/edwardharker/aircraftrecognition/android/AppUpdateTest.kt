package com.edwardharker.aircraftrecognition.android

import android.content.pm.PackageInfo
import com.edwardharker.aircraftrecognition.android.preferences.AircraftSharedPreferences
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.mockito.Mockito.verify

class AppUpdateTest {
    @Test
    fun `returns true when new version is greater than old version`() {
        val fakePreferences = FakeAircraftSharedPreferences(
            thatReturns = AppUpdate.LAST_APP_VERSION_KEY to 1
        )
        val appUpdate = AppUpdate(
            sharedPreferences = fakePreferences,
            packageInfo = PackageInfo().apply {
                versionCode = 2
            }
        )

        assertThat(appUpdate.hasUpdated(), equalTo(true))
    }

    @Test
    fun `saves new version code when new version is greater than old version`() {
        val newVersionCode = 2
        val mockPreferences = mock<AircraftSharedPreferences> {
            on { getInt(AppUpdate.LAST_APP_VERSION_KEY, 0) } doReturn 1
        }
        val appUpdate = AppUpdate(
            sharedPreferences = mockPreferences,
            packageInfo = PackageInfo().apply {
                versionCode = newVersionCode
            }
        )

        appUpdate.hasUpdated()

        verify(mockPreferences).saveInt(AppUpdate.LAST_APP_VERSION_KEY, newVersionCode)
    }

    @Test
    fun `does not call preferences on subsequent calls to hasUpdated`() {
        val mockPreferences = mock<AircraftSharedPreferences> {
            on { getInt(AppUpdate.LAST_APP_VERSION_KEY, 0) } doReturn 1
        }
        val appUpdate = AppUpdate(
            sharedPreferences = mockPreferences,
            packageInfo = PackageInfo().apply {
                versionCode = 2
            }
        )

        appUpdate.hasUpdated()
        appUpdate.hasUpdated()
        appUpdate.hasUpdated()

        verify(mockPreferences).getInt(AppUpdate.LAST_APP_VERSION_KEY, 0)
    }
}