package com.bencestumpf.itunessample.presentation.details

import android.widget.TextView
import butterknife.BindView
import com.bencestumpf.itunessample.R
import com.bencestumpf.itunessample.di.Injector
import com.bencestumpf.itunessample.presentation.common.MVPActivity

class DetailsActivity : MVPActivity<DetailsPresenter, DetailsView>(), DetailsView {
    companion object {

        const val EXTRA_SONG_ID = "EXTRA_SONG_ID"
    }

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
