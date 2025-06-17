package com.rma.catalist.breeds.gallery

import com.rma.catalist.breeds.api.model.CatImageApiModel
import com.rma.catalist.breeds.domain.Breed

interface BreedGalleryContract {

    data class UiState(
        val loading: Boolean = true,
        val images: List<CatImageApiModel> = emptyList(),
        val error: Throwable? = null,
        var breedId: String = "",
    )

    sealed class UiEvent {
        data class ImageClicked(val imageUrl: String)  : UiEvent()
    }


}