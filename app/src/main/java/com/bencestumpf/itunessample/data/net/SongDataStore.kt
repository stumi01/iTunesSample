package com.bencestumpf.itunessample.data.net

import android.accounts.NetworkErrorException
import com.bencestumpf.itunessample.data.net.services.ITunesApiService
import com.bencestumpf.itunessample.data.net.services.SongResultResponseModel
import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.domain.repositories.SongRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongDataStore @Inject constructor(private val apiService: ITunesApiService) : SongRepository.Remote {
    private val ENTITY_SONG: String = "song"

    override fun searchSongs(query: String): Single<List<Song>> {
        return apiService.search(query, ENTITY_SONG).map {
            if (it.isSuccessful) {
                return@map it.body()?.results?.map(this::mapRepo)!!.filter { song -> song.title != null }
            }
            throw NetworkErrorException("Error happened searching for songs with query: $query")
        }
    }

    private fun mapRepo(model: SongResultResponseModel): Song =
        Song(
            model.trackId,
            model.trackName.orEmpty(),
            model.trackTimeMillis,
            model.trackPrice,
            model.currency,
            model.artistName,
            model.previewUrl,
            model.collectionName,
            model.artworkUrl30,
            model.artworkUrl100,
            model.releaseDate,
            model.primaryGenreName
        )

}
