package com.bencestumpf.itunessample.presentation.details

import com.bencestumpf.itunessample.di.scopes.ActivityScope
import com.bencestumpf.itunessample.presentation.common.MVPPresenter
import javax.inject.Inject

@ActivityScope
class DetailsPresenter @Inject constructor() : MVPPresenter<DetailsView>() {
    fun setup(songID: Long?) {
        songID?.let {
            getSong(it)
        } ?: view?.showError()
    }

    private fun getSong(it: Long) {
        //TODO
    }

}
