package com.edwardharker.aircraftrecognition.filter.picker

import rx.Observable

interface FilterPickerShowResetUseCase {
    fun shouldShowReset(): Observable<Boolean>
}
