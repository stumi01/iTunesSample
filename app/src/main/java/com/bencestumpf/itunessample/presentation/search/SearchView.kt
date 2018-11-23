package com.bencestumpf.itunessample.presentation.search

interface SearchView {
    fun showLoading()
    fun showContent(data: List<String>)

}
