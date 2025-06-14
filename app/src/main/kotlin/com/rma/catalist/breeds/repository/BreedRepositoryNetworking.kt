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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
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
                    val allImagesResponse = breedApi.getAllImagesForBreed(breed.id, 10)
                    if (allImagesResponse.isNotEmpty()) {
                        val imageDb = allImagesResponse[0].asImageDb()
                        for (imageElement in allImagesResponse) database.imageDao()
                            .insert(imageElement.asImageDb())
                        breed.imageUrl = imageDb.url
                        database.breedDao().update(breed)
                        delay(400)
                    }
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

    suspend fun  observeAllImagesForBreedAlbum(breedId: String) = database.imageDao().getAllForBreed(breedId)


    suspend fun  fetchSearchBreeds(query: String, dataAllBreeds: List<Breed>): List<Breed>?{
        Log.d("BreedDebug", "fetchSearchBreeds() called")
        Log.d("BreedDebug", "CELA LISTA U REPOSIT: "+dataAllBreeds.toString())



        return dataAllBreeds.filter { it.name.contains(query, ignoreCase = true) }
    }


}