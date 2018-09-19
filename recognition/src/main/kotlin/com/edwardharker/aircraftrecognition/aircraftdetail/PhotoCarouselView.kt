package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Image

interface PhotoCarouselView {
    fun showImages(images: List<Image>)
    fun dismiss()
}
