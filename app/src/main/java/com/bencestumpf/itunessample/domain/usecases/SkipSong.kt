package com.bencestumpf.itunessample.domain.usecases

import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.domain.repositories.SongRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SkipSong @Inject constructor(private val songRepository: SongRepository) : Usecase<SkipSong.Parameter, Song>() {

    override fun getSubscribable(): Single<Song> =
        when (parameter!!.direction) {
            SkipSong.Direction.NEXT -> songRepository.getNextSong(parameter!!.currentSong)
            SkipSong.Direction.PREVIOUS -> songRepository.getPreviousSong(parameter!!.currentSong)
        }


    enum class Direction {
        NEXT, PREVIOUS
    }

    class Parameter(var direction: Direction, var currentSong: Long)

}
