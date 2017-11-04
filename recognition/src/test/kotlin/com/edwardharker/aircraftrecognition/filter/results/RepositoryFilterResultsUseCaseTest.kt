package com.edwardharker.aircraftrecognition.filter.results

import com.edwardharker.aircraftrecognition.filter.FakeSelectedFilterOptions
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.FakeAircraftRepository
import org.junit.Test
import rx.observers.TestSubscriber

class RepositoryFilterResultsUseCaseTest {

    private val fakeSelectedFilterOptions = FakeSelectedFilterOptions()
    private val fakeAircraftRepository = FakeAircraftRepository()
    private val testSubscriber = TestSubscriber<List<Aircraft>>()

    @Test
    fun filterEmissionsEmitFilteredAircraft() {
        val aircraft = listOf(Aircraft())
        val filters = mapOf(Pair("key", "value"))
        fakeAircraftRepository.thatEmits(aircraft, filters)

        val repositoryFilterResultsUseCase =
                RepositoryFilterResultsUseCase(fakeSelectedFilterOptions, fakeAircraftRepository)

        repositoryFilterResultsUseCase.filteredAircraft().subscribe(testSubscriber)
        fakeSelectedFilterOptions.subject.onNext(filters)
        testSubscriber.assertValues(aircraft)
    }
}
