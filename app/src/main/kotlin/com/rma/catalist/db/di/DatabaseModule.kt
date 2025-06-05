package com.rma.catalist.db.di

import com.rma.catalist.db.AppDatabase
import com.rma.catalist.db.AppDatabaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(builder: AppDatabaseBuilder): AppDatabase {
        return builder.build()
    }
}