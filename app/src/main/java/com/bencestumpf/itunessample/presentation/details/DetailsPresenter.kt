package com.bencestumpf.itunessample.presentation.details

import android.util.Log
import com.bencestumpf.itunessample.di.scopes.ActivityScope
import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.domain.usecases.GetSong
import com.bencestumpf.itunessample.domain.usecases.SkipSong
import com.bencestumpf.itunessample.presentation.common.MVPPresenter
import javax.inject.Inject

@ActivityScope
class DetailsPresenter @Inject constructor(private val getSong: GetSong, private val skipSong: SkipSong) :
    MVPPresenter<DetailsView>() {

    private var currentSong = -1L
    private var playerPaused: Boolean = false

    fun setup(songID: Long?) {
        songID?.let {
            getSong(it)
        } ?: view?.showError()
    }

    private fun getSong(songID: Long) {
        execute(getSong, songID, { onSongArrived(it, false) }, this::onError)
    }

    private fun onSongArrived(song: Song, playing: Boolean) {
        currentSong = song.trackId
        view?.showContent(song)
        if (playing) {
            view?.playSong(song.previewUrl!!)
            playerPaused = false
        }
    }

    fun onNextClick(isPlaying: Boolean) {
        playerPaused = false
        execute(
            skipSong,
            SkipSong.Parameter(SkipSong.Direction.NEXT, currentSong),
            { onSongArrived(it, isPlaying) },
            this::onError
        )
    }

    private fun onError(throwable: Throwable) {
        Log.e(DetailsPresenter::class.java.simpleName, "ERROR", throwable)
        view?.showError()
    }

    fun onPreviousClick(isPlaying: Boolean) {
        playerPaused = false
        execute(
            skipSong,
            SkipSong.Parameter(SkipSong.Direction.PREVIOUS, currentSong),
            { onSongArrived(it, isPlaying) },
            this::onError
        )
    }

    fun onShareClick() {
        view?.shareSong()
    }

    fun onPlayClick() {
        if (playerPaused) {
            playerPaused = false
            view?.showPauseButton()
            view?.returnSong()
        } else {
            execute(getSong, currentSong, {
                playerPaused = false
                view?.showPauseButton()
                view?.playSong(it.previewUrl!!)
            }, this::onError)
        }
    }

    fun onStopped() {
        playerPaused = false
        view?.showPlayButton()
    }

    fun onPauseClick() {
        playerPaused = true
        view?.showPlayButton()
        view?.pauseSong()
    }

}
