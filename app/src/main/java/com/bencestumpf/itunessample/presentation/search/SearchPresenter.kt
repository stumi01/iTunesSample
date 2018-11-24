package com.bencestumpf.itunessample.presentation.search

import android.util.Log
import com.bencestumpf.itunessample.di.scopes.ActivityScope
import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.domain.usecases.SearchForQuery
import com.bencestumpf.itunessample.domain.usecases.SortSongs
import com.bencestumpf.itunessample.presentation.common.MVPPresenter
import javax.inject.Inject

@ActivityScope
class SearchPresenter @Inject constructor(
    private val searchForQuery: SearchForQuery,
    private val sortSongs: SortSongs
) : MVPPresenter<SearchView>() {

    fun doSearch(query: String) {
        view?.let {
            it.showLoading()
            execute(searchForQuery, query, this::onDataArrived, this::onError)
        }
    }

    private fun onError(error: Throwable) {
        Log.d("STUMI", "On Error")
    }

    private fun onDataArrived(data: List<Song>) {
        Log.d("STUMI", "onDataArrived $data")
        view?.showContent(data)

    }

    fun onSongClick(songID: Long) {
        view?.navigateToDetailsView(songID)
    }

    fun onGenreSort() {
        execute(sortSongs, SortSongs.Parameter(SortSongs.Sort.GENRE), this::onDataArrived, this::onError)
    }

    fun onLengthSort() {
        execute(sortSongs, SortSongs.Parameter(SortSongs.Sort.LENGTH), this::onDataArrived, this::onError)
    }

    fun onPriceSort() {
        execute(sortSongs, SortSongs.Parameter(SortSongs.Sort.PRICE), this::onDataArrived, this::onError)
    }

}
