package com.rma.catalist.breeds.api

import android.adservices.adid.AdId
import com.rma.catalist.breeds.api.model.BreedApiModel
import com.rma.catalist.breeds.api.model.CatImageApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface BreedApi {

    @GET("breeds")
    suspend fun  getAllBreeds(): List<BreedApiModel>


    @GET("breeds/{breed_id}")
    suspend fun getBreed(
        @Path("id") breedId: String,
    ): BreedApiModel

    @GET("images/{image_id}")
    suspend fun getImageUrl(
        @Path("image_id") imageId: String?
    ): CatImageApiModel

}