package com.rma.catalist.breeds.di

import com.rma.catalist.breeds.api.BreedApi
import com.rma.catalist.breeds.repository.BreedRepositoryNetworking
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object BreedModule {

    @Provides
    @Singleton
    fun provideBreedApi(retrofit: Retrofit) = retrofit.create(BreedApi::class.java)


//    @Provides
//    @Singleton
//    fun provideBreedRepository(
//        breedApi: BreedApi,
//        okHttpClient: OkHttpClient
//    ): BreedRepository = BreedRepositoryNetworking(breedApi, okHttpClient)

}