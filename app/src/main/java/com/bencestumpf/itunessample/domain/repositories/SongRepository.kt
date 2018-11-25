package com.bencestumpf.itunessample.domain.repositories

import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.helper.OpenClass
import io.reactivex.Single

@OpenClass
class SongRepository(private val remote: Remote, private val cache: Cache) {

    fun searchWith(query: String): Single<List<Song>> = remote.searchSongs(query).doOnSuccess { cache.update(it) }

    fun getSong(songID: Long): Single<Song> = cache.getSong(songID)

    fun getNextSong(currentSongID: Long): Single<Song> {
        return cache.getNextSong(currentSongID)
    }

    fun getPreviousSong(currentSongID: Long): Single<Song> {
        return cache.getPreviousSong(currentSongID)
    }

    fun sortByGenre(): Single<List<Song>> {
        return cache.sortByGenre()
    }

    fun sortByLength(): Single<List<Song>> {
        return cache.sortByLength()
    }

    fun sortByPrice(): Single<List<Song>> {
        return cache.sortByPrice()
    }

    interface Remote {
        fun searchSongs(query: String): Single<List<Song>>
    }

    interface Cache {
        fun getSong(id: Long): Single<Song>
        fun update(songs: List<Song>)
        fun getNextSong(currentSongID: Long): Single<Song>
        fun getPreviousSong(currentSongID: Long): Single<Song>
        fun sortByGenre(): Single<List<Song>>
        fun sortByLength(): Single<List<Song>>
        fun sortByPrice(): Single<List<Song>>
    }

}
