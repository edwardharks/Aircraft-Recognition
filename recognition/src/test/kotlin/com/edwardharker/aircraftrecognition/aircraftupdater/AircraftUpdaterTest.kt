package com.edwardharker.aircraftrecognition.aircraftupdater

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import com.edwardharker.aircraftrecognition.repository.FakeAircraftRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.mockito.Mockito.*
import rx.Observable
import rx.schedulers.Schedulers

class AircraftUpdaterTest {
    private val aircraft = listOf(Aircraft())

    @Test
    fun `saves aircaft from static repository in aircraft repository when aircraft repository is empty`() {
        val mockRepository = mock<AircraftRepository> {
            on { allAircraftCount() } doReturn Observable.just(0L)
        }
        val aircraftUpdater = AircraftUpdater(
            ioScheduler = Schedulers.trampoline(),
            mainScheduler = Schedulers.trampoline(),
            staticAircraftRepository = FakeAircraftRepository().thatEmits(aircraft),
            remoteAircraftRepository = FakeAircraftRepository(),
            aircraftRepository = mockRepository
        )

        aircraftUpdater.update()

        verify(mockRepository).saveAircraft(aircraft)
    }

    @Test
    fun `doesn not saves aircaft from static repository in aircraft repository when aircraft repository is not empty`() {
        val mockRepository = mock<AircraftRepository> {
            on { allAircraftCount() } doReturn Observable.just(1L)
        }
        val aircraftUpdater = AircraftUpdater(
            ioScheduler = Schedulers.trampoline(),
            mainScheduler = Schedulers.trampoline(),
            staticAircraftRepository = FakeAircraftRepository().thatEmits(aircraft),
            remoteAircraftRepository = FakeAircraftRepository(),
            aircraftRepository = mockRepository
        )

        aircraftUpdater.update()

        verify(mockRepository, never()).saveAircraft(aircraft)
    }


    @Test
    fun `saves aircaft from remote repository in aircraft repository`() {
        val mockRepository = mock<AircraftRepository> {
            on { allAircraftCount() } doReturn Observable.never()
        }
        val aircraftUpdater = AircraftUpdater(
            ioScheduler = Schedulers.trampoline(),
            mainScheduler = Schedulers.trampoline(),
            staticAircraftRepository = FakeAircraftRepository(),
            remoteAircraftRepository = FakeAircraftRepository().thatEmits(aircraft),
            aircraftRepository = mockRepository
        )

        aircraftUpdater.update()

        verify(mockRepository).saveAircraft(aircraft)
    }

    @Test
    fun `does not saves aircraft when aircraft repository emits empty list`() {
        val mockRepository = mock<AircraftRepository> {
            on { allAircraftCount() } doReturn Observable.never()
        }
        val aircraftUpdater = AircraftUpdater(
            ioScheduler = Schedulers.trampoline(),
            mainScheduler = Schedulers.trampoline(),
            staticAircraftRepository = FakeAircraftRepository(),
            remoteAircraftRepository = FakeAircraftRepository().thatEmits(listOf()),
            aircraftRepository = mockRepository
        )

        aircraftUpdater.update()

        verify(mockRepository, never()).saveAircraft(com.nhaarman.mockito_kotlin.any())
    }
}