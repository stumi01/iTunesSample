package com.bencestumpf.itunessample.presentation.search

import android.util.Log
import com.bencestumpf.itunessample.di.scopes.ActivityScope
import com.bencestumpf.itunessample.domain.usecases.SearchForQuery
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class SearchPresenter @Inject constructor(private val searchForQuery: SearchForQuery) {

    var disposables: CompositeDisposable = CompositeDisposable()

    var view: SearchView? = null

    fun doSearch(query: String) {
        Log.d("STUMI", "Busy doing search $query")
        view?.let {
            it.showLoading()
            execute(searchForQuery.withParams(query), this::onDataArrived, this::onError)
        }

    }

    private fun onError(error: Throwable) {
        Log.d("STUMI", "On Error")
    }

    private fun onDataArrived(data: List<String>) {
        Log.d("STUMI", "onDataArrived " + data)
        view?.showContent(data)

    }

    fun execute(
        usecase: SearchForQuery,
        onNext: (List<String>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        disposables.add(
            usecase.getSubscribable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError)
        )
    }

    fun onPause() {
        disposables.dispose()
        disposables = CompositeDisposable()
    }

    fun attach(view: SearchView) {
        this.view = view
    }

    fun detach() {
        view = null
    }


}
