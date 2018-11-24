package com.bencestumpf.itunessample.domain.usecases

import com.bencestumpf.itunessample.domain.model.Song
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchForQuery @Inject constructor() {
    private lateinit var query: String

    fun withParams(query: String): SearchForQuery {
        this.query = query
        return this
    }

    fun getSubscribable(): Single<List<Song>> {
        val song = Song(
            "Title", "Artist", "details", "album", "releaseData",
            "cover", "length", "genre", "price"
        )
        return Single.just(listOf(song, song, song, song, song, song, song))
    }
}
