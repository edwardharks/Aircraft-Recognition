package com.edwardharker.aircraftrecognition.repository.realm.mapper

import com.edwardharker.aircraftrecognition.model.Image
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmImage

fun imageToRealmImage(image: Image): RealmImage {
    return RealmImage(image.url, image.width, image.height)
}

fun realmImageToImage(realmImage: RealmImage): Image {
    return Image(realmImage.url, realmImage.width, realmImage.height)
}
