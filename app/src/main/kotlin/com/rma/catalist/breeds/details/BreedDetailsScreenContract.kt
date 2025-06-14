package com.rma.catalist.breeds.details

import com.rma.catalist.breeds.domain.Breed

interface BreedDetailsScreenContract {

    data class UiState(
        val breedId: String,
        val loading: Boolean = true,
        val data: Breed? = null,
        val error: Throwable? = null,

    )

    sealed class UiEvent {
        data class OpenedScreen(val id: String) : UiEvent()
        object OnWikipediaClicked : UiEvent()
        object OnGalleryClicked : UiEvent()
    }

    sealed class SideEffect {
        data class NavigateToWikipediaUrl(val url: String) : SideEffect()
        data class NavigateToBreedGallery(val breedId: String) : SideEffect()
    }
}