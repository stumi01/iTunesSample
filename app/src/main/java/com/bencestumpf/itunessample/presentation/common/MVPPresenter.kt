package com.bencestumpf.itunessample.presentation.common

import com.bencestumpf.itunessample.domain.usecases.Usecase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class MVPPresenter<V : MVPView> {

    var disposables: CompositeDisposable = CompositeDisposable()

    protected var view: V? = null

    fun attachView(view: V) {
        this.view = view
    }

    fun <Parameter, ResultType> execute(
        usecase: Usecase<Parameter, ResultType>,
        parameter: Parameter,
        onNext: (ResultType) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        disposables.add(
            usecase.withParams(parameter).getSubscribable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError)
        )
    }


    fun onPause() {
        disposables.dispose()
        disposables = CompositeDisposable()
    }

    fun detachView() {

        view = null
    }

}