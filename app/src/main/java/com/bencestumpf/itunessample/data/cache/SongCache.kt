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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortByLength(): Single<List<Song>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortByPrice(): Single<List<Song>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

private fun java.util.ArrayList<Song>.findSongById(searchedSongId: Long): Song? =
    this.find { song -> song.trackId == searchedSongId }


class SongNotFoundException(message: String) : Exception(message)