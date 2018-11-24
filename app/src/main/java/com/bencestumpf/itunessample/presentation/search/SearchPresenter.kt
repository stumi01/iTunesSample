package com.bencestumpf.itunessample.presentation.search

import android.util.Log
import com.bencestumpf.itunessample.di.scopes.ActivityScope
import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.domain.usecases.SearchForQuery
import com.bencestumpf.itunessample.presentation.common.MVPPresenter
import javax.inject.Inject

@ActivityScope
class SearchPresenter @Inject constructor(private val searchForQuery: SearchForQuery) : MVPPresenter<SearchView>() {

    fun doSearch(query: String) {
        view?.let {
            it.showLoading()
            execute(searchForQuery.withParams(query), this::onDataArrived, this::onError)
        }

    }

    private fun onError(error: Throwable) {
        Log.d("STUMI", "On Error")
    }

    private fun onDataArrived(data: List<Song>) {
        Log.d("STUMI", "onDataArrived $data")
        view?.showContent(data)

    }

    public fun onSongClick(songID: Long) {
        view?.navigateToDetailsView(songID)
    }

}
