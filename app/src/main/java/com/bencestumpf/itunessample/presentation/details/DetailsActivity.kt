package com.bencestumpf.itunessample.presentation.details

import android.support.v4.app.ShareCompat
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.bencestumpf.itunessample.R
import com.bencestumpf.itunessample.di.Injector
import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.presentation.common.MVPActivity

class DetailsActivity : MVPActivity<DetailsPresenter, DetailsView>(), DetailsView {

    companion object {
        const val EXTRA_SONG_ID = "EXTRA_SONG_ID"
    }

    @BindView(R.id.error_view)
    lateinit var errorView: View
    @BindView(R.id.content_view)
    lateinit var content: View

    @BindView(R.id.song_details_title)
    lateinit var title: TextView
    @BindView(R.id.song_details_album)
    lateinit var album: TextView
    @BindView(R.id.song_details_artist)
    lateinit var artist: TextView

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
    }

    @OnClick(R.id.song_details_next)
    fun onNextClick() {
        presenter.onNextClick()
    }

    @OnClick(R.id.song_details_previous)
    fun onPreviousClick() {
        presenter.onPreviousClick()
    }

    @OnClick(R.id.song_details_share)
    fun onShareClick() {
        presenter.onShareClick()
    }

    override fun shareSong() {
        ShareCompat.IntentBuilder.from(this)
            .setText("Hey check out this song: ${artist.text} - ${title.text} ")
            .setType("text/plain")
            .startChooser()
    }

}
