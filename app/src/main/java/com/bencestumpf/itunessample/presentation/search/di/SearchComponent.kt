package com.bencestumpf.itunessample.presentation.search.di

import com.bencestumpf.itunessample.di.scopes.ActivityScope
import com.bencestumpf.itunessample.presentation.search.SearchActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface SearchComponent {

    fun inject(activity: SearchActivity)

}
