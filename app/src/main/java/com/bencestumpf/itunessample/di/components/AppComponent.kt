package com.bencestumpf.itunessample.di.components

import com.bencestumpf.itunessample.di.modules.AppModule
import com.bencestumpf.itunessample.presentation.details.di.DetailsComponent
import com.bencestumpf.itunessample.presentation.search.di.SearchComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun searchComponent(): SearchComponent
    fun detailsComponent(): DetailsComponent
}
