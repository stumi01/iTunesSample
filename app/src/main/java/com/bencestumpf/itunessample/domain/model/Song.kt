package com.bencestumpf.itunessample.domain.model

data class Song(
    val title: String, val artist: String, val details: String,
    val album: String, val releaseDate: String, val cover: String,
    val length: String, val genre: String, val price: String
)
