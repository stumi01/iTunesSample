package com.bencestumpf.itunessample.domain.model

import java.util.*

data class Song(
    val trackId: Long, val title: String?, val trackTimeMillis: Long?, val trackPrice: Float?, val currency: String?,
    val artistName: String?, val previewUrl: String?, val album: String?, val artworkThumbnailUrl: String?,
    val artworkUrl: String?, val releaseDate: Date?, val genre: String?
)
