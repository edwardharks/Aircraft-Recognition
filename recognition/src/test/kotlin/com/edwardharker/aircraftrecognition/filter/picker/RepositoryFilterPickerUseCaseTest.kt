package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.filter.FakeSelectedFilterOptions
import com.edwardharker.aircraftrecognition.model.Filter
import com.edwardharker.aircraftrecognition.repository.FakeAircraftRepository
import com.edwardharker.aircraftrecognition.repository.FakeFilterRepository
import org.junit.Test
import rx.observers.TestSubscriber

class RepositoryFilterPickerUseCaseTest {

    @Test
    fun emitsFiltersWhenNoFiltersSelected() {
        val filters = listOf(Filter())
        val fakeSelectedFilterOptions = FakeSelectedFilterOptions()
        val useCase = RepositoryFilterPickerUseCase(
                FakeFilterRepository().thatEmits(filters),
                fakeSelectedFilterOptions,
                FakeAircraftRepository(),
                { filter, _ -> filter }
        )
        val testSubscriber = TestSubscriber.create<List<Filter>>()
        useCase.filters().subscribe(testSubscriber)
        fakeSelectedFilterOptions.subject.onNext(emptyMap())
        testSubscriber.assertValues(filters)
    }
}
