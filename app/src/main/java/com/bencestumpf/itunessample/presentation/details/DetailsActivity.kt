package com.bencestumpf.itunessample.presentation.details

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.PowerManager
import android.support.v4.app.ShareCompat
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.bencestumpf.itunessample.R
import com.bencestumpf.itunessample.di.Injector
import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.presentation.common.MVPActivity
import com.bumptech.glide.Glide

class DetailsActivity : MVPActivity<DetailsPresenter, DetailsView>(), DetailsView {
    companion object {
        const val EXTRA_SONG_ID = "EXTRA_SONG_ID"
    }

    private var mediaPlayer: MediaPlayer? = null

    @BindView(R.id.error_view)
    lateinit var errorView: View
    @BindView(R.id.content_view)
    lateinit var content: View

    @BindView(R.id.song_details_artwork)
    lateinit var artwork: ImageView
    @BindView(R.id.song_details_title)
    lateinit var title: TextView
    @BindView(R.id.song_details_album)
    lateinit var album: TextView
    @BindView(R.id.song_details_artist)
    lateinit var artist: TextView
    @BindView(R.id.song_details_play_pause)
    lateinit var playPause: ImageButton

    override fun injectDependencies() {
        Injector.getAppComponent()
            .detailsComponent()
            .inject(this)
    }

    override fun setupView() {
        val songID = intent.getLongExtra(EXTRA_SONG_ID, -1)
        presenter.setup(songID)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_navigate_before_white_48)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun getLayoutId(): Int = R.layout.activity_details

    override fun getView(): DetailsView = this

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun showError() {
        content.visibility = View.GONE
        errorView.visibility = View.VISIBLE
    }

    override fun showContent(song: Song) {
        errorView.visibility = View.GONE
        content.visibility = View.VISIBLE

        title.text = song.title
        album.text = song.album
        artist.text = song.artistName
        Glide.with(this)
            .load(song.artworkUrl)
            .into(artwork)
    }

    @OnClick(R.id.song_details_next)
    fun onNextClick() {
        presenter.onNextClick(mediaPlayer?.isPlaying == true)
    }

    @OnClick(R.id.song_details_previous)
    fun onPreviousClick() {
        presenter.onPreviousClick(mediaPlayer?.isPlaying == true)
    }

    @OnClick(R.id.song_details_share)
    fun onShareClick() {
        presenter.onShareClick()
    }

    @OnClick(R.id.song_details_play_pause)
    fun onPlayPauseClick() {
        if (mediaPlayer?.isPlaying == true) {
            presenter.onPauseClick()
        } else {
            presenter.onPlayClick()
        }
    }

    override fun shareSong() {
        ShareCompat.IntentBuilder.from(this)
            .setText(getString(R.string.share_message, artist.text, title.text))
            .setType("text/plain")
            .startChooser()
    }

    override fun playSong(previewUrl: String) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setAudioAttributes(AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_MUSIC).build())
            } else {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
            }
            setDataSource(previewUrl)
            setOnCompletionListener { presenter.onStopped() }
            prepare()
            start()
        }
    }

    override fun showPauseButton() {
        playPause.setBackgroundResource(R.drawable.baseline_pause_24)
    }

    override fun showPlayButton() {
        playPause.setBackgroundResource(R.drawable.baseline_play_arrow_24)
    }

    override fun pauseSong() {
        mediaPlayer?.pause()
    }

    override fun returnSong() {
        mediaPlayer?.start()
    }

    override fun releaseMediaPlayer() {
        mediaPlayer?.release()
    }
}
