package com.edwardharker.aircraftrecognition.ui.aircraftdetail

import com.edwardharker.aircraftrecognition.aircraftdetail.AircraftDetailPresenter
import com.edwardharker.aircraftrecognition.aircraftdetail.AircraftDetailUseCase
import com.edwardharker.aircraftrecognition.aircraftdetail.RepositoryAircraftDetailUseCase
import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import rx.android.schedulers.AndroidSchedulers.mainThread

private fun aircraftDetailUseCase(): AircraftDetailUseCase =
        RepositoryAircraftDetailUseCase(aircraftRepository())

fun aircraftDetailPresenter(): AircraftDetailPresenter =
        AircraftDetailPresenter(mainThread(), { aircraftDetailUseCase().aircraft(it) })