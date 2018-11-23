package com.bencestumpf.itunessample.domain.usecases

import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchForQuery @Inject constructor() {
    private lateinit var query: String

    fun withParams(query: String): SearchForQuery {
        this.query = query
        return this
    }

    fun getSubscribable(): Single<List<String>> {
        return Single.just(listOf("one"))
    }
}
