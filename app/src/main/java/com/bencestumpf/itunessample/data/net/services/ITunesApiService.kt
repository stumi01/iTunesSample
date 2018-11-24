package com.bencestumpf.itunessample.data.net.services

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.*

interface ITunesApiService {

    @Headers("Content-Type: application/json")
    @GET("/search")
    fun search(@Query("term") query: String, @Query("entity") entity: String): Single<Response<SearchResponseModel>>

}

data class SearchResponseModel(val resultCount: Int, val results: List<SongResultResponseModel>)

data class SongResultResponseModel(
    val trackId: Long, val trackName: String?, val trackTimeMillis: Long, val trackPrice: Float, val currency: String,
    val artistName: String, val previewUrl: String, val collectionName: String, val artworkUrl30: String,
    val artworkUrl100: String, val releaseDate: Date, val primaryGenreName: String
)