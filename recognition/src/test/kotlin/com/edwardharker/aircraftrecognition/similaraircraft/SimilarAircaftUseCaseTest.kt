package com.edwardharker.aircraftrecognition.similaraircraft

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.FakeAircraftRepository
import org.junit.Test
import rx.observers.TestSubscriber

class SimilarAircaftUseCaseTest {

    private val id = "aircraft1"
    private val key = "key"
    private val value = "value"
    private val filterOptions = mapOf(key to value)
    private val aircraft1 = Aircraft(id = id, filterOptions = filterOptions)
    private val aircraft2 = Aircraft(id = "aircraft2", filterOptions = filterOptions)

    private val testSubscriber = TestSubscriber<List<Aircraft>>()

    @Test
    fun `emits similar aircraft`() {
        val fakeAircraftRepository = FakeAircraftRepository()
                .thatEmits(aircraft = aircraft1, forId = id)
                .thatEmits(aircraft = listOf(aircraft1, aircraft2), forFilter = filterOptions)
        val useCase = SimilarAircaftUseCase(fakeAircraftRepository)
        useCase.invoke(id).subscribe(testSubscriber)
        testSubscriber.assertValue(listOf(aircraft2))
        testSubscriber.assertCompleted()
    }
}
