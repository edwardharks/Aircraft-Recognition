package com.edwardharker.aircraftrecognition.android

import android.content.pm.PackageInfo
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class FirstInstallTest {
    @Test
    fun `saves the version`() {
        val fakePreferences = FakeAircraftSharedPreferences(
            containsReturns = "FIRST_INSTALL_VERSION" to false
        )
        val firstInstall = FirstInstall(
            sharedPreferences = fakePreferences,
            packageInfo = PackageInfo().apply {
                versionCode = VERSION
            }
        )

        firstInstall.saveVersion()

        assertThat(fakePreferences.saveIntCalls.size, equalTo(1))
        assertThat(
            fakePreferences.saveIntCalls.first(),
            equalTo("FIRST_INSTALL_VERSION" to VERSION)
        )
    }

    @Test
    fun `does not save the version when already saved`() {
        val fakePreferences = FakeAircraftSharedPreferences(
            containsReturns = "FIRST_INSTALL_VERSION" to true
        )
        val firstInstall = FirstInstall(
            sharedPreferences = fakePreferences,
            packageInfo = PackageInfo()
        )

        firstInstall.saveVersion()

        assertThat(fakePreferences.saveIntCalls.size, equalTo(0))
    }

    @Test
    fun `return first install version`() {
        val fakePreferences = FakeAircraftSharedPreferences(
            containsReturns = "FIRST_INSTALL_VERSION" to true,
            getIntReturns = "FIRST_INSTALL_VERSION" to VERSION
        )
        val firstInstall = FirstInstall(
            sharedPreferences = fakePreferences,
            packageInfo = PackageInfo()
        )

        assertThat(firstInstall.firstInstallVersion, equalTo(VERSION))
    }

    @Test
    fun `saves first install version if it doesn't exist when getting version`() {
        val fakePreferences = FakeAircraftSharedPreferences(
            containsReturns = "FIRST_INSTALL_VERSION" to false,
            getIntReturns = "FIRST_INSTALL_VERSION" to VERSION
        )
        val firstInstall = FirstInstall(
            sharedPreferences = fakePreferences,
            packageInfo = PackageInfo().apply {
                versionCode = VERSION
            }
        )

        firstInstall.firstInstallVersion

        assertThat(fakePreferences.saveIntCalls.size, equalTo(1))
        assertThat(
            fakePreferences.saveIntCalls.first(),
            equalTo("FIRST_INSTALL_VERSION" to VERSION)
        )
    }

    private companion object {
        private const val VERSION = 2
    }
}