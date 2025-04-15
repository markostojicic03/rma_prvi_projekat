package com.rma.catalist.breeds.domain

import kotlinx.coroutines.flow.Flow

interface BreedRepository {

    suspend fun getAllBreeds() : List<Breed>
    suspend fun getBreedById(breedId:Int) : Breed?
}