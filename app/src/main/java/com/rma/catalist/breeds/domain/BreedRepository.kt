package com.rma.catalist.breeds.domain

interface BreedRepository {

    suspend fun getAllBreeds() : List<Breed>
}