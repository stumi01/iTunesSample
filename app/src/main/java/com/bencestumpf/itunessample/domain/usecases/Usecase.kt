package com.bencestumpf.itunessample.domain.usecases

import io.reactivex.Single

interface Usecase<ReturnType> {

    fun getSubscribable(): Single<ReturnType>

}