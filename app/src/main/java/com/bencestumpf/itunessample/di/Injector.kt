package com.bencestumpf.itunessample.di

import android.support.annotation.VisibleForTesting
import com.bencestumpf.itunessample.ITunesSampleApplication
import com.bencestumpf.itunessample.di.components.AppComponent
import com.bencestumpf.itunessample.di.components.DaggerAppComponent
import com.bencestumpf.itunessample.di.modules.AppModule

enum class Injector {
    INSTANCE;

    lateinit var applicationComponent: AppComponent

    companion object {

        internal fun initialize(app: ITunesSampleApplication) {
            val applicationComponent = DaggerAppComponent.builder()
                .appModule(AppModule(app))
                .build()
            INSTANCE.applicationComponent = applicationComponent
        }

        fun getAppComponent(): AppComponent {
            return INSTANCE.applicationComponent
        }

        @VisibleForTesting
        fun setComponent(appComponent: AppComponent) {
            INSTANCE.applicationComponent = appComponent
        }
    }
}
