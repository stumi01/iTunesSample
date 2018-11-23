package com.bencestumpf.itunessample.di.components

import com.bencestumpf.itunessample.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

}
