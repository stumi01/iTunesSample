package com.bencestumpf.itunessample.helper

import android.support.test.InstrumentationRegistry
import com.bencestumpf.itunessample.ITunesSampleApplication
import com.bencestumpf.itunessample.di.Injector
import com.bencestumpf.itunessample.di.components.AppComponent
import com.bencestumpf.itunessample.di.modules.AppModule
import com.bencestumpf.itunessample.di.modules.RepositoryModule
import it.cosenonjaviste.daggermock.DaggerMock

fun espressoDaggerMockRule() = DaggerMock.rule<AppComponent>(AppModule(app), RepositoryModule()) {
    set { component ->
        Injector.setComponent(component)
    }
}

private val app: ITunesSampleApplication
    get() =
        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as ITunesSampleApplication
