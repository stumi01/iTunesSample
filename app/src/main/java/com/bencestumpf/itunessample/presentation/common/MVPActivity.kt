package com.bencestumpf.itunessample.presentation.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import javax.inject.Inject

abstract class MVPActivity<P : MVPPresenter<V>, V : MVPView> : AppCompatActivity() {

    @Inject
    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        ButterKnife.bind(this)
        injectDependencies()
        setupView()
        presenter.attachView(getView())
    }

    abstract fun injectDependencies()

    abstract fun setupView()

    abstract fun getLayoutId(): Int

    abstract fun getView(): V

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}