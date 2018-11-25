package com.bencestumpf.itunessample.di.modules

import com.bencestumpf.itunessample.data.cache.SongCache
import com.bencestumpf.itunessample.data.net.SongDataStore
import com.bencestumpf.itunessample.domain.repositories.SongRepository
import com.bencestumpf.itunessample.helper.OpenClass
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@OpenClass
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideGitRepositoryProvider(dataStore: SongDataStore, cache: SongCache): SongRepository {
        return SongRepository(dataStore, cache)
    }
}
