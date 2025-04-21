package com.rma.catalist.breeds.repository

import android.util.Log
import coil3.network.HttpException
import com.rma.catalist.breeds.api.BreedApi
import com.rma.catalist.breeds.api.model.BreedApiModel
import com.rma.catalist.breeds.domain.Breed
import com.rma.catalist.breeds.domain.BreedRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
        Log.d("BreedDebug", "fetchCatImage() called")

        return withContext(Dispatchers.IO) {
            try {

                breedApi.getImageUrl(reference_image_id).imageUrl

            } catch (e: Exception) {
                if (e is CancellationException) Log.d("BreedDebug", "Error fetching image URL - Job canceled")
                else if(e is IllegalArgumentException) Log.d("BreedDebug","Error fetching image URL - image id doesnt exist" )
                else if(e is retrofit2.HttpException ){
                    Log.e("BreedDebug", "HTTP 429")
                    //retryLoadingImage(reference_image_id = reference_image_id)
                }
                else Log.e("BreedDebug", "Error fetching image URL",e)

                val genericCatImage = "https://logowik.com/content/uploads/images/cat8600.jpg"

                genericCatImage
            }
        }
    }

    suspend fun retryLoadingImage(
        times: Int = 3,
        delayMs: Long = 1500L,
        reference_image_id: String?
    ): String? {
        repeat(times - 1) { attempt ->
            try {
                breedApi.getImageUrl(reference_image_id).imageUrl
            } catch ( e :retrofit2.HttpException) {
                if (e.message?.contains("retrofit2.HttpException: HTTP 429") == true) {
                    Log.w("BreedDebug", "Again error with loading image http 429")
                    delay(delayMs)
                } else {
                    Log.e("BreedDebug", "Again error with loading image, http error but not 429")
                }
            } catch (e: Exception) {
                Log.e("BreedDebug", "Unexpected error with loading image")
            }
        }
        return try {
            breedApi.getImageUrl(reference_image_id).imageUrl
        } catch (e: Exception) {
            Log.e("BreedDebug", "Final attempt failed")
            null
        }
    }

    override suspend fun fetchBreedById(breedId: String): BreedApiModel? {
        Log.d("BreedDebug", "fetchBreedsById() called")
        return  withContext(Dispatchers.IO){
            try {
                val response = breedApi.getBreed(breedId)
                response
            }catch (e: Exception){
                Log.e("BreedDebug", "Exception while fetching breed by ID", e)
                null
            }
        }
    }

    override  suspend fun  fetchSearchBreeds(query: String, dataAllBreeds: List<Breed>): List<Breed>?{
        Log.d("BreedDebug", "fetchSearchBreeds() called")
        Log.d("BreedDebug", "CELA LISTA U REPOSIT: "+dataAllBreeds.toString())



        return dataAllBreeds.filter { it.name.contains(query, ignoreCase = true) }
    }


}