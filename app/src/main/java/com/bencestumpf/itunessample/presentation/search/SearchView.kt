package com.bencestumpf.itunessample.presentation.search

import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.presentation.common.MVPView

interface SearchView : MVPView {
    fun showLoading()
    fun showContent(data: List<Song>)
    fun showError()
    fun showEmpty()
    fun navigateToDetailsView(songID: Long)
}
