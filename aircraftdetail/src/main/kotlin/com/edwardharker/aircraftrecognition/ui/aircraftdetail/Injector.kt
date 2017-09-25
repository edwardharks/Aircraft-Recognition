package com.edwardharker.aircraftrecognition.ui.aircraftdetail

import com.edwardharker.aircraftrecognition.aircraftdetail.AircraftDetailPresenter
import com.edwardharker.aircraftrecognition.aircraftdetail.AircraftDetailUseCase
import com.edwardharker.aircraftrecognition.aircraftdetail.PhotoCarouselPresenter
import com.edwardharker.aircraftrecognition.aircraftdetail.RepositoryAircraftDetailUseCase
import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.similaraircraft.SimilarAircaftUseCase
import com.edwardharker.aircraftrecognition.similaraircraft.SimilarAircraftPresenter
import com.edwardharker.aircraftrecognition.youtube.youtubeAvailabilityChecker
import rx.android.schedulers.AndroidSchedulers.mainThread

private fun aircraftDetailUseCase(): AircraftDetailUseCase =
        RepositoryAircraftDetailUseCase(aircraftRepository())

fun aircraftDetailPresenter(): AircraftDetailPresenter =
        AircraftDetailPresenter(
                mainThread(),
                { aircraftDetailUseCase().aircraft(it) },
                youtubeAvailabilityChecker()::isYoutubeAvailable
        )

fun photoCarouselPresenter(): PhotoCarouselPresenter =
        PhotoCarouselPresenter(mainThread(), { aircraftDetailUseCase().aircraft(it) })

private fun similarAircraftUseCase(): SimilarAircaftUseCase =
        SimilarAircaftUseCase(aircraftRepository())

fun similarAircraftPresenter(): SimilarAircraftPresenter =
        SimilarAircraftPresenter(mainThread(), similarAircraftUseCase())
