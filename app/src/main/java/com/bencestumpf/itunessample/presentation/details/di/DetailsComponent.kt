package com.bencestumpf.itunessample.presentation.details.di

import com.bencestumpf.itunessample.di.scopes.ActivityScope
import com.bencestumpf.itunessample.presentation.details.DetailsActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface DetailsComponent {
    fun inject(activity: DetailsActivity)
}
