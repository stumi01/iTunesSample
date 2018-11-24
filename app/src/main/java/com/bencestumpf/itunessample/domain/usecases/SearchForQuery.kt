package com.bencestumpf.itunessample.domain.usecases

import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.domain.repositories.SongRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchForQuery @Inject constructor(private val songRepository: SongRepository) {
    private lateinit var query: String

    fun withParams(query: String): SearchForQuery {
        this.query = query
        return this
    }

    fun getSubscribable(): Single<List<Song>> {
        return songRepository.searchWith(query)
    }
}
