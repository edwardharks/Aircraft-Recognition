package com.edwardharker.aircraftrecognition.model


data class Aircraft(
        val id: String = "",
        val name: String = "",
        val manufacturer: String = "",
        val shortDescription: String = "",
        val longDescription: String = "",
        val attribution: String = "",
        val attributionUrl: String = "",
        val metaData: Map<String, String> = emptyMap(),
        val filterOptions: Map<String, String> = emptyMap(),
        val images: List<Image> = emptyList(),
        val youtubeVideos: List<YoutubeVideo> = emptyList()
)

val Aircraft.displayName: String
    get() = String.format("%s %s", manufacturer, name)
