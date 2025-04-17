package com.rma.catalist.breeds.domain

import com.rma.catalist.breeds.api.BreedApi
import com.rma.catalist.breeds.api.model.BreedApiModel
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient

interface BreedRepository {

    suspend fun fetchAllBreeds(): List<BreedApiModel>
    suspend fun fetchCatImage(reference_imageId: String?): String?

}