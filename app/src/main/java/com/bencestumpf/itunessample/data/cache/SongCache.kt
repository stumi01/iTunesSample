package com.bencestumpf.itunessample.data.cache

import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.domain.repositories.SongRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongCache @Inject constructor() : SongRepository.Cache {

    private val songs = ArrayList<Song>()

    override fun update(songs: List<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)
    }

    override fun getSong(id: Long): Single<Song> {
        return Single.create { emitter ->
            songs.findSongById(id)
                ?.let { emitter.onSuccess(it) }
                ?: emitter.onError(SongNotFoundException("Searched id: $id"))
        }
    }

    override fun getNextSong(currentSongID: Long): Single<Song> {
        return Single.create { emitter ->
            songs.findSongById(currentSongID)
                ?.let { emitter.onSuccess(getNextOrFirst(it)) }
                ?: emitter.onError(SongNotFoundException("Searched id: $currentSongID"))
        }
    }

    private fun getNextOrFirst(it: Song): Song {
        val indexOfNext = songs.indexOf(it) + 1
        return if (songs.size == indexOfNext) {
            songs.first()
        } else {
            songs[indexOfNext]
        }
    }

    override fun getPreviousSong(currentSongID: Long): Single<Song> {
        return Single.create { emitter ->
            songs.findSongById(currentSongID)
                ?.let { emitter.onSuccess(getPreviousOrLast(it)) }
                ?: emitter.onError(SongNotFoundException("Searched id: $currentSongID"))
        }
    }

    private fun getPreviousOrLast(it: Song): Song {
        val indexOfPrevious = songs.indexOf(it) - 1
        return if (-1 == indexOfPrevious) {
            songs.last()
        } else {
            songs[indexOfPrevious]
        }
    }

    override fun sortByGenre(): Single<List<Song>> {
        return songs.sortAndGetAsSingle { it.genre }
    }

    override fun sortByLength(): Single<List<Song>> {
        return songs.sortAndGetAsSingle { it.trackTimeMillis }
    }

    override fun sortByPrice(): Single<List<Song>> {
        return songs.sortAndGetAsSingle { it.trackPrice }
    }

}

private fun java.util.ArrayList<Song>.findSongById(searchedSongId: Long): Song? =
    this.find { song -> song.trackId == searchedSongId }

private inline fun <T : Comparable<T>> java.util.ArrayList<Song>.sortAndGetAsSingle(crossinline selector: (Song) -> T?): Single<List<Song>> =
    Single.create {
        this.sortBy(selector)
        it.onSuccess(this)
    }

class SongNotFoundException(message: String) : Exception(message)