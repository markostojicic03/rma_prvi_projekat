package com.rma.catalist.breeds.repository

import android.util.Log
import com.rma.catalist.breeds.api.BreedApi
import com.rma.catalist.breeds.api.model.BreedApiModel
import com.rma.catalist.breeds.domain.Breed
import com.rma.catalist.breeds.map.asBreedDb
import com.rma.catalist.breeds.map.asImageDb
import com.rma.catalist.db.AppDatabase
import com.rma.catalist.db.dao.ImageDao
import com.rma.catalist.db.entities.BreedDb
import com.rma.catalist.db.entities.ImageDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import javax.inject.Inject

class BreedRepositoryNetworking @Inject constructor(
    private val breedApi: BreedApi,
    private val okHttpClient: OkHttpClient,
    private val database: AppDatabase,
){

    init {
        Log.d("BreedDebug", "BreedRepositoryNetworking created")
    }

    suspend fun fetchAllBreedsAndStore() =  withContext(Dispatchers.IO) {
        val users = breedApi.getAllBreeds()
        database.breedDao().insertAll(
            list = users.map { it.asBreedDb() },
        )
    }

    fun observeAllBreedsRepository() = database.breedDao().observeAllBreedsDao() // vraca flow podatka iz baze


    fun observeBreedDetails(breedId: String) = database.breedDao().observeBreedById(breedId)// isto kao sto sam gore uradio za sve rase, tako ovde samo za jednu specificnu

    suspend fun fetchAndStoreImagesForAllBreeds() = withContext(Dispatchers.IO) {
        val breedList = database.breedDao().getAll()

        breedList.forEach { breed ->
            val refId = breed.reference_image_id
            if (!refId.isNullOrBlank()) {
                try {
                    val imageResponse = breedApi.getImageUrl(refId)
                    val imageDb = imageResponse.asImageDb()
                    database.imageDao().insert(imageDb)
                    breed.imageUrl = imageDb.url
                    database.breedDao().update(breed)
                    delay(400)
                } catch (e: Exception) {
                    Log.e("BreedDebug", "Failed for breed ${breed.id}"+ e)
                }
            }
            else{
                breed.imageUrl ="https://i.pinimg.com/originals/a7/e8/89/a7e889effe08ecbede2ddaafbecdbd66.jpg"// default slika ako nema slike iz api-ja
                database.breedDao().update(breed)
            }
        }
    }

/*
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
*/

   /* suspend fun fetchBreedById(breedId: String): BreedApiModel? {
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
    }*/

    suspend fun  fetchSearchBreeds(query: String, dataAllBreeds: List<Breed>): List<Breed>?{
        Log.d("BreedDebug", "fetchSearchBreeds() called")
        Log.d("BreedDebug", "CELA LISTA U REPOSIT: "+dataAllBreeds.toString())



        return dataAllBreeds.filter { it.name.contains(query, ignoreCase = true) }
    }


}