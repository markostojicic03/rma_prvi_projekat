package com.rma.catalist.breeds.repository

import android.util.Log
import com.rma.catalist.breeds.api.BreedApi
import com.rma.catalist.breeds.api.model.BreedApiModel
import com.rma.catalist.breeds.domain.Breed
import com.rma.catalist.breeds.domain.BreedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import javax.inject.Inject

class BreedRepositoryNetworking @Inject constructor(
    private val breedApi: BreedApi,
    private val okHttpClient: OkHttpClient
): BreedRepository{

    init {
        Log.d("BreedDebug", "BreedRepositoryNetworking created")
    }


    override suspend fun fetchAllBreeds(

    ): List<BreedApiModel> {
        Log.d("BreedDebug", "fetchAllBreeds() called")
        return withContext(Dispatchers.IO) {
            try {
                val response = breedApi.getAllBreeds()
                Log.d("BreedDebug", "Response size: ${response.size}")
                response.forEachIndexed { index, breed ->
                    Log.d("BreedDebug indx", "[$index] -> ${breed.name}")
                }
                response
            } catch (e: Exception) {
                Log.e("BreedDebug", "Exception while fetching breeds", e)
                emptyList()
            }
        }
    }

    override suspend fun fetchCatImage(reference_image_id: String?): String? {
        return withContext(Dispatchers.IO) {
            try {
                breedApi.getImageUrl(reference_image_id).imageUrl
            } catch (e: Exception) {
                Log.e("BreedDebug", "Error fetching image URL")
                null
            }
        }
    }


}