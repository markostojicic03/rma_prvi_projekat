package com.rma.catalist.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UserAccountStoreModule {
    @Provides           // tells Dagger how to provide instances of a type
    @Singleton
    fun provideAuthDataStore(@ApplicationContext context: Context): DataStore<UserAccount> =
        DataStoreFactory.create(
            produceFile = { context.dataStoreFile("dataStore.txt") },
            serializer = UserAccountSerializer()
        )


}