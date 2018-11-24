package com.bencestumpf.itunessample.domain.repositories

import com.bencestumpf.itunessample.domain.model.Song
import io.reactivex.Single

class SongRepository(private val remote: Remote) {
    fun searchWith(query: String): Single<List<Song>> {
        return remote.searchSongs(query)
    }


    interface Remote {
        fun searchSongs(query: String): Single<List<Song>>
    }

}
