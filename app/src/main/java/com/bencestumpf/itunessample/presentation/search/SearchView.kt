package com.bencestumpf.itunessample.presentation.search

import com.bencestumpf.itunessample.domain.model.Song

interface SearchView {
    fun showLoading()
    fun showContent(data: List<Song>)

}
