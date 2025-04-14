package com.rma.catalist.di

import com.rma.catalist.breeds.domain.BreedRepository
import com.rma.catalist.breeds.repository.BreedRepositoryMock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BreedsModule {

    @Provides
    fun bindsBreedRepository(): BreedRepository = BreedRepositoryMock()

}