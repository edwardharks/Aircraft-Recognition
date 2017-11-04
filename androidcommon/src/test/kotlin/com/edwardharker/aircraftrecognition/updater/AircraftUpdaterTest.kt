package com.edwardharker.aircraftrecognition.updater

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import com.edwardharker.aircraftrecognition.repository.FakeAircraftRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import rx.Observable
import rx.schedulers.Schedulers

class AircraftUpdaterTest {

    private val aircraft = listOf(Aircraft())

    @Test
    fun `saves aircaft from static repository in aircraft repository when aircraft repository is empty`() {
        val mockRepository = mock<AircraftRepository> {
            on { allAircraft() } doReturn Observable.just(listOf())
        }
        val aircraftUpdater = AircraftUpdater(
                ioScheduler = Schedulers.trampoline(),
                mainScheduler = Schedulers.trampoline(),
                staticAircraftRepository = FakeAircraftRepository().thatEmits(aircraft),
                remoteAircraftRepository = FakeAircraftRepository(),
                aircraftRepository = mockRepository
        )

        aircraftUpdater.update()

        Mockito.verify(mockRepository).saveAircraft(aircraft)
    }

    @Test
    fun `doesn not saves aircaft from static repository in aircraft repository when aircraft repository is not empty`() {
        val mockRepository = mock<AircraftRepository> {
            on { allAircraft() } doReturn Observable.just(aircraft)
        }
        val aircraftUpdater = AircraftUpdater(
                ioScheduler = Schedulers.trampoline(),
                mainScheduler = Schedulers.trampoline(),
                staticAircraftRepository = FakeAircraftRepository().thatEmits(aircraft),
                remoteAircraftRepository = FakeAircraftRepository(),
                aircraftRepository = mockRepository
        )

        aircraftUpdater.update()

        Mockito.verify(mockRepository, never()).saveAircraft(aircraft)
    }


    @Test
    fun `saves aircaft from remote repository in aircraft repository`() {
        val mockRepository = mock<AircraftRepository> {
            on { allAircraft() } doReturn Observable.never()
        }
        val aircraftUpdater = AircraftUpdater(
                ioScheduler = Schedulers.trampoline(),
                mainScheduler = Schedulers.trampoline(),
                staticAircraftRepository = FakeAircraftRepository(),
                remoteAircraftRepository = FakeAircraftRepository().thatEmits(aircraft),
                aircraftRepository = mockRepository
        )

        aircraftUpdater.update()

        Mockito.verify(mockRepository).saveAircraft(aircraft)
    }
}