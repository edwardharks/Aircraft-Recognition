package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.filter.FakeSelectedFilterOptions
import org.junit.Test
import rx.observers.TestSubscriber

class SelectedFilterOptionsBasedFilterPickerShowResetUseCaseTest {
    @Test
    fun `emits true when there are selected filter options`() {
        val testSubscriber = TestSubscriber<Boolean>()
        val selectedFilterOptions = FakeSelectedFilterOptions()
        val useCase = SelectedFilterOptionsBasedFilterPickerShowResetUseCase(
            selectedFilterOptions = selectedFilterOptions
        )
        useCase.shouldShowReset().subscribe(testSubscriber)

        selectedFilterOptions.subject.onNext(mapOf("filter_option" to "filter_value"))

        testSubscriber.assertValues(true)
    }

    @Test
    fun `emits false when there are no selected filter options`() {
        val testSubscriber = TestSubscriber<Boolean>()
        val selectedFilterOptions = FakeSelectedFilterOptions()
        val useCase = SelectedFilterOptionsBasedFilterPickerShowResetUseCase(
            selectedFilterOptions = selectedFilterOptions
        )
        useCase.shouldShowReset().subscribe(testSubscriber)

        selectedFilterOptions.subject.onNext(emptyMap())

        testSubscriber.assertValues(false)
    }
}