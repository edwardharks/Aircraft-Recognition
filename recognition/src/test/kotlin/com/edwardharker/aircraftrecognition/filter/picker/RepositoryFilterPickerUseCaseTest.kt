package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.model.Filter
import com.edwardharker.aircraftrecognition.repository.FakeFilterRepository
import org.junit.Test
import rx.observers.TestSubscriber

class RepositoryFilterPickerUseCaseTest {

    @Test
    fun emitsFilters() {
        val filters = listOf(Filter())
        val fakeFilterRepository = FakeFilterRepository().thatEmits(filters)
        val useCase = RepositoryFilterPickerUseCase(fakeFilterRepository)
        val testSubscriber = TestSubscriber.create<List<Filter>>()
        useCase.filters().subscribe(testSubscriber)
        testSubscriber.assertValues(filters)
    }
}
