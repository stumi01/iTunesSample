package com.bencestumpf.itunessample.di.modules

import com.bencestumpf.itunessample.ITunesSampleApplication
import com.bencestumpf.itunessample.helper.OpenClass
import dagger.Module

@OpenClass
@Module(includes = [RetrofitModule::class, RepositoryModule::class])
class AppModule(val app: ITunesSampleApplication) {

}