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
        data class OpenedScreen(val breedId: String) : UiEvent()
    }

    sealed class SideEffect {
        // No side effects here
    }
}