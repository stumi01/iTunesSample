package com.bencestumpf.itunessample.presentation.details

import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.presentation.common.MVPView

interface DetailsView : MVPView {
    fun showError()
    fun showContent(song: Song)
    fun shareSong()
    fun playSong(previewUrl: String)
    fun showPauseButton()
    fun showPlayButton()
    fun pauseSong()
    fun returnSong()
}
