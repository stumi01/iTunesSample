package com.bencestumpf.itunessample.di.modules

import com.bencestumpf.itunessample.data.net.SongDataStore
import com.bencestumpf.itunessample.domain.repositories.SongRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideGitRepositoryProvider(dataStore: SongDataStore): SongRepository {
        return SongRepository(dataStore)
    }
}
