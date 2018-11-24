package com.bencestumpf.itunessample.presentation.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import android.widget.SearchView
import butterknife.BindView
import com.bencestumpf.itunessample.R
import com.bencestumpf.itunessample.di.Injector
import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.presentation.common.MVPActivity
import com.bencestumpf.itunessample.presentation.details.DetailsActivity
import com.bencestumpf.itunessample.presentation.details.DetailsActivity.Companion.EXTRA_SONG_ID
import com.bencestumpf.itunessample.presentation.search.SearchView as InnerSearchView


class SearchActivity : MVPActivity<SearchPresenter, InnerSearchView>(), InnerSearchView {

    @BindView(R.id.welcome_view)
    lateinit var welcomeView: View

    @BindView(R.id.empty_view)
    lateinit var emptyView: View

    @BindView(R.id.error_view)
    lateinit var errorView: View

    @BindView(R.id.search_swipeRefresh)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @BindView(R.id.search_list)
    lateinit var searchRecyclerView: RecyclerView

    private lateinit var adapter: SongsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSearchIntentIfExists(intent)
    }

    override fun injectDependencies() {
        Injector.getAppComponent()
            .searchComponent()
            .inject(this)
    }

    override fun setupView() {
        setupRecycler()
    }

    private fun setupRecycler() {
        adapter = SongsAdapter(this, presenter::onSongClick)
        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        searchRecyclerView.isNestedScrollingEnabled = false
        searchRecyclerView.adapter = adapter
        searchRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getView(): InnerSearchView = this

    override fun onNewIntent(intent: Intent) {
        handleSearchIntentIfExists(intent)
    }

    private fun handleSearchIntentIfExists(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            invalidateOptionsMenu()
            val query = intent.getStringExtra(SearchManager.QUERY)
            presenter.doSearch(query)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        (menu.findItem(R.id.sort_genre)).setOnMenuItemClickListener { presenter.onGenreSort();true }
        (menu.findItem(R.id.sort_length)).setOnMenuItemClickListener { presenter.onLengthSort();true }
        (menu.findItem(R.id.sort_price)).setOnMenuItemClickListener { presenter.onPriceSort();true }

        return true
    }

    override fun showLoading() {
        welcomeView.visibility = View.GONE
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.visibility = View.VISIBLE
    }

    override fun showError() {
        swipeRefreshLayout.isRefreshing = false
        swipeRefreshLayout.visibility = View.GONE
        welcomeView.visibility = View.GONE
        emptyView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
    }

    override fun showEmpty() {
        swipeRefreshLayout.isRefreshing = false
        swipeRefreshLayout.visibility = View.GONE
        welcomeView.visibility = View.GONE
        errorView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
    }

    override fun showContent(data: List<Song>) {
        swipeRefreshLayout.isRefreshing = false
        swipeRefreshLayout.isEnabled = false
        adapter.setData(data)
    }

    override fun navigateToDetailsView(songID: Long) {
        startActivity(Intent(this, DetailsActivity::class.java).apply {
            putExtra(EXTRA_SONG_ID, songID)
        })
    }

}
