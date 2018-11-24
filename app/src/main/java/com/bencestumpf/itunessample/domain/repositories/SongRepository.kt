package com.bencestumpf.itunessample.domain.repositories

import com.bencestumpf.itunessample.domain.model.Song
import io.reactivex.Single

class SongRepository(private val remote: Remote, private val cache: Cache) {

    fun searchWith(query: String): Single<List<Song>> = remote.searchSongs(query).doOnSuccess { cache.update(it) }

    fun getSong(songID: Long): Single<Song> = cache.getSong(songID)

    fun getNextSong(currentSongID: Long): Single<Song> {
        return cache.getNextSong(currentSongID)
    }

    fun getPreviousSong(currentSongID: Long): Single<Song> {
        return cache.getPreviousSong(currentSongID)
    }

    interface Remote {
        fun searchSongs(query: String): Single<List<Song>>
    }

    interface Cache {
        fun getSong(id: Long): Single<Song>
        fun update(songs: List<Song>)
        fun getNextSong(currentSongID: Long): Single<Song>
        fun getPreviousSong(currentSongID: Long): Single<Song>
    }


}
