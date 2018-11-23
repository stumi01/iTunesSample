package com.bencestumpf.itunessample

import android.app.Application
import com.bencestumpf.itunessample.di.Injector

class ITunesSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeInjection()
    }

    private fun initializeInjection() {
        Injector.initialize(this)
    }
}