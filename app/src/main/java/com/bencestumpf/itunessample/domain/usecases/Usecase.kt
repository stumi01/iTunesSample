package com.bencestumpf.itunessample.domain.usecases

import io.reactivex.Single

abstract class Usecase<Parameter, ReturnType> {

    protected var parameter: Parameter? = null

    fun withParams(parameter: Parameter): Usecase<Parameter, ReturnType> {
        this.parameter = parameter
        return this
    }

    abstract fun getSubscribable(): Single<ReturnType>


}