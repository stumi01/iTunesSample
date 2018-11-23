package com.bencestumpf.itunessample.presentation.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import android.widget.SearchView
import butterknife.BindView
import butterknife.ButterKnife
import com.bencestumpf.itunessample.R
import com.bencestumpf.itunessample.di.Injector
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), com.bencestumpf.itunessample.presentation.search.SearchView {
    @Inject
    lateinit var presenter: SearchPresenter

    @BindView(R.id.search_swipeRefresh)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @BindView(R.id.search_list)
    lateinit var trendingRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        Injector.getAppComponent()
            .searchComponent()
            .inject(this)
        presenter.attach(this)
        handleSearchIntentIfExists(intent)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun onNewIntent(intent: Intent) {
        handleSearchIntentIfExists(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return true
    }

    private fun handleSearchIntentIfExists(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
            presenter.doSearch(query)
        }
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.visibility = View.VISIBLE
    }

    override fun showContent(data: List<String>) {
        swipeRefreshLayout.isRefreshing = false
        swipeRefreshLayout.isEnabled = false
        //TODO show content
    }
}
