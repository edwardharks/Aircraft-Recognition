package com.edwardharker.aircraftrecognition.repository.realm.mapper

import com.edwardharker.aircraftrecognition.model.Image
import com.edwardharker.aircraftrecognition.repository.realm.model.RealmImage

fun imageToRealmImage(image: Image): RealmImage =
        RealmImage(image.url, image.width, image.height)

fun realmImageToImage(realmImage: RealmImage): Image =
        Image(realmImage.url, realmImage.width, realmImage.height)