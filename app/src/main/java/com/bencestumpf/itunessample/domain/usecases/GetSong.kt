package com.bencestumpf.itunessample.domain.usecases

import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.domain.repositories.SongRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSong @Inject constructor(private val songRepository: SongRepository) : Usecase<Long, Song>() {

    override fun getSubscribable(): Single<Song> {
        return songRepository.getSong(parameter!!)
    }

}
