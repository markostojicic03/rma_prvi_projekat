package com.rma.catalist.breeds.repository

import android.util.Log
import coil3.network.HttpException
import com.rma.catalist.breeds.api.BreedApi
import com.rma.catalist.breeds.api.model.BreedApiModel
import com.rma.catalist.breeds.domain.Breed
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import javax.inject.Inject

class BreedRepositoryNetworking @Inject constructor(
    private val breedApi: BreedApi,
    private val okHttpClient: OkHttpClient
){

    init {
        Log.d("BreedDebug", "BreedRepositoryNetworking created")
    }


    suspend fun fetchAllBreeds(

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

     suspend fun fetchCatImage(reference_image_id: String?): String? {
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


    suspend fun fetchBreedById(breedId: String): BreedApiModel? {
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

    suspend fun  fetchSearchBreeds(query: String, dataAllBreeds: List<Breed>): List<Breed>?{
        Log.d("BreedDebug", "fetchSearchBreeds() called")
        Log.d("BreedDebug", "CELA LISTA U REPOSIT: "+dataAllBreeds.toString())



        return dataAllBreeds.filter { it.name.contains(query, ignoreCase = true) }
    }


}