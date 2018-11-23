package com.bencestumpf.itunessample.di.modules

import com.bencestumpf.itunessample.ITunesSampleApplication
import dagger.Module

@Module(includes = [RetrofitModule::class])
class AppModule(val app: ITunesSampleApplication) {

}