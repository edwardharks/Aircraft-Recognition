package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.FakeAircraftRepository
import org.junit.Test
import rx.observers.TestSubscriber

class RepositoryAircraftDetailUseCaseTest {
    private val testSubscriber = TestSubscriber<Aircraft>()

    @Test
    fun emitsAircraftForId() {
        val id = "id"
        val aircraft = Aircraft()
        val fakeAircraftRepository = FakeAircraftRepository().thatEmits(aircraft, id)
        val useCase = RepositoryAircraftDetailUseCase(fakeAircraftRepository)
        useCase.getAircraft(id).subscribe(testSubscriber)
        testSubscriber.assertValue(aircraft)
        testSubscriber.assertCompleted()
    }
}