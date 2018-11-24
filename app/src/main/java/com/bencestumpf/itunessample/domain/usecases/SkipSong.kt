package com.bencestumpf.itunessample.domain.usecases

import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.domain.repositories.SongRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SkipSong @Inject constructor(private val songRepository: SongRepository) : Usecase<Song> {

    private lateinit var direction: Direction
    private var currentSong: Long = -1

    fun withParams(direction: Direction, currentSong: Long): SkipSong {
        this.direction = direction
        this.currentSong = currentSong
        return this
    }


    override fun getSubscribable(): Single<Song> =
        when (direction) {
            SkipSong.Direction.NEXT -> songRepository.getNextSong(currentSong)
            SkipSong.Direction.PREVIOUS -> songRepository.getPreviousSong(currentSong)
        }

    enum class Direction {
        NEXT, PREVIOUS
    }

}
