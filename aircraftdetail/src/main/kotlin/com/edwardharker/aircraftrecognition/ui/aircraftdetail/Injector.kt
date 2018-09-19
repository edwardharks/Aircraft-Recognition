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

private fun aircraftDetailUseCase(): AircraftDetailUseCase {
    return RepositoryAircraftDetailUseCase(aircraftRepository = aircraftRepository())
}

fun aircraftDetailPresenter(): AircraftDetailPresenter {
    return AircraftDetailPresenter(
        mainScheduler = mainThread(),
        aircraftDetailUseCase = aircraftDetailUseCase(),
        isYoutubeAvailable = youtubeAvailabilityChecker()::isYoutubeAvailable
    )
}

fun photoCarouselPresenter(): PhotoCarouselPresenter {
    return PhotoCarouselPresenter(
        mainScheduler = mainThread(),
        aircraftDetailUseCase = aircraftDetailUseCase()
    )
}

private fun similarAircraftUseCase(): SimilarAircaftUseCase {
    return SimilarAircaftUseCase(aircraftRepository = aircraftRepository())
}

fun similarAircraftPresenter(): SimilarAircraftPresenter {
    return SimilarAircraftPresenter(
        mainScheduler = mainThread(),
        similarAircraft = similarAircraftUseCase()
    )
}
