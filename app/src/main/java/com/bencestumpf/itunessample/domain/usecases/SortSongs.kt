package com.bencestumpf.itunessample.domain.usecases

import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.domain.repositories.SongRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SortSongs @Inject constructor(private val songRepository: SongRepository) :
    Usecase<SortSongs.Parameter, List<Song>>() {

    override fun getSubscribable(): Single<List<Song>> =
        when (parameter!!.sort) {
            SortSongs.Sort.GENRE -> songRepository.sortByGenre()
            SortSongs.Sort.LENGTH -> songRepository.sortByLength()
            SortSongs.Sort.PRICE -> songRepository.sortByPrice()
        }


    class Parameter(val sort: Sort)

    enum class Sort { GENRE, LENGTH, PRICE }
}

