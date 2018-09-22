package com.edwardharker.aircraftrecognition.analytics

import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.never

class ReleaseOnlyCompositeEventAnalyticsTest {
    private val mockEventAnalytics = Mockito.mock(EventAnalytics::class.java)
    private val event = Event(EventType.SELECT_CONTENT)
    private val screenView = ScreenView("screen")

    @Test
    fun logsEventWhenReleaseBuild() {
        val eventAnalytics = ReleaseOnlyCompositeEventAnalytics(
            releaseBuild = true,
            eventAnalytics = mockEventAnalytics
        )

        eventAnalytics.logEvent(event)
        Mockito.verify(mockEventAnalytics).logEvent(event)
    }

    @Test
    fun logsScreenViewWhenReleaseBuild() {
        val eventAnalytics = ReleaseOnlyCompositeEventAnalytics(
            releaseBuild = true,
            eventAnalytics = mockEventAnalytics
        )

        eventAnalytics.logScreenView(screenView)
        Mockito.verify(mockEventAnalytics).logScreenView(screenView)
    }

    @Test
    fun neverLogsEventWhenNotReleaseBuild() {
        val eventAnalytics = ReleaseOnlyCompositeEventAnalytics(
            releaseBuild = false,
            eventAnalytics = mockEventAnalytics
        )

        eventAnalytics.logEvent(event)
        Mockito.verify(mockEventAnalytics, never()).logEvent(event)
    }

    @Test
    fun neverLogsScreenViewWhenNotReleaseBuild() {
        val eventAnalytics = ReleaseOnlyCompositeEventAnalytics(
            releaseBuild = false,
            eventAnalytics = mockEventAnalytics
        )

        eventAnalytics.logScreenView(screenView)
        Mockito.verify(mockEventAnalytics, never()).logScreenView(screenView)
    }
}
